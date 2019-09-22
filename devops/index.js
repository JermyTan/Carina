const mysql = require('mysql');
const axios = require('axios');
const csv = require('csvtojson');
const fs = require('fs');
const dateFns = require('date-fns');
const _ = require('lodash');

const apis = [
  'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2',
  'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=500',
  'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=1000',
  'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=1500',
  'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=2000',
  'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=2500'
];

function addTimeStamp(data, timestamp) {
  data.forEach((entry) => entry.timestamp = timestamp);
  return data;
}

// Returns data from LTA Data Mall
async function getData(apiUrl, timestamp) {
  try {
    const response = await axios.get(apiUrl, { "headers": { "AccountKey": "PjPcZIN2SS+LjtXvfFlTIA==", "accept": "application/json" } });
    const results = response.data.value;
    return addTimeStamp(results, timestamp);
  } catch (error) {
    console.error(error);
    return [];
  }
}

// Configurations for MySQL
const dbConfig = {
    host: 'localhost',
    user: 'lta',
    password: 'lta',
    database: 'lta',
}

function getConnection() {
  return mysql.createConnection(dbConfig);
}

function getSQLInsertCommand(date) {
  const map = {
    1: 'INSERT INTO carpark_availability_monday VALUES ?',
    2: 'INSERT INTO carpark_availability_tuesday VALUES ?',
    3: 'INSERT INTO carpark_availability_wednesday VALUES ?',
    4: 'INSERT INTO carpark_availability_thursday VALUES ?',
    5: 'INSERT INTO carpark_availability_friday VALUES ?',
    6: 'INSERT INTO carpark_availability_saturday VALUES ?',
    0: 'INSERT INTO carpark_availability_sunday VALUES ?'
  }
  const index = dateFns.getDay(date);
  return map[index];
}

function getSQLDeleteCommand(date) {
  const map = {
    1: 'DELETE FROM carpark_availability_monday WHERE hour = ',
    2: 'DELETE FROM carpark_availability_tuesday WHERE hour = ',
    3: 'DELETE FROM carpark_availability_wednesday WHERE hour = ',
    4: 'DELETE FROM carpark_availability_thursday WHERE hour = ',
    5: 'DELETE FROM carpark_availability_friday WHERE hour = ',
    6: 'DELETE FROM carpark_availability_saturday WHERE hour = ',
    0: 'DELETE FROM carpark_availability_sunday WHERE hour = '
  }
  const index = dateFns.getDay(date);
  return map[index];
}

// Returns the name of the day of the week
function getNameOfDay(date) {
  const map = {
    1: 'MONDAY',
    2: 'TUESDAY',
    3: 'WEDNESDAY',
    4: 'THURSDAY',
    5: 'FRIDAY',
    6: 'SATURDAY',
    0: 'SUNDAY'
  }
  const index = dateFns.getDay(date);
  return map[index];
}

// Returns the hour of that day
function getHourOfDay(date) {
  return dateFns.getHours(date);
}

// Returns the date from timestamp in milliseconds
function getDate(timestamp) {
  return new Date(_.toInteger(timestamp));
}

// Returns 
function formatString(str) {
  return _.trim(str).split(' ').join('_');
}

// Returns formatted data to insert into db
function formatData(row) {
  const { 
    CarParkID, Area, Development, Location, AvailableLots, LotType, Agency, timestamp
  } = row;

  const loc = Location.split(" ");
  const latitude = _.toNumber(loc[0]);
  const longitude = _.toNumber(loc[1]);

  const date = getDate(timestamp);
  const day = getNameOfDay(date);
  const hour = getHourOfDay(date);

  const available_lots = _.toInteger(AvailableLots);

  const hasNaN = _.isNaN(latitude) || _.isNaN(longitude) || _.isNaN(available_lots);

  const data = {
    carpark_id: CarParkID + '_' + formatString(Area) + '_' + formatString(Development) + '_' + formatString(LotType) + '_' + day + '_' + hour,
    area: Area,
    development: Development,
    latitude,
    longitude,
    available_lots,
    lot_type: LotType,
    agency: Agency,
    hour,
    day,
    timestamp: _.toInteger(timestamp)
  }

  if (hasNaN) {
    return null;
  }
  return data;
}

async function readData(csvFilePath) {
  return await csv().fromFile(csvFilePath);
}

function insertData(data, date) {
  const connection = getConnection();
  connection.connect();
  const SQL_COMMAND = getSQLInsertCommand(date);
  const query = connection.query(SQL_COMMAND, [data], function(err, result) {
    console.log(err);
    console.log(result);
  });
  connection.end();
}

function deleteData(date) {
  const connection = getConnection();
  connection.connect();
  const SQL_COMMAND = getSQLDeleteCommand(date) + dateFns.getHours(date);
  const query = connection.query(SQL_COMMAND, function(err, result) {
    console.log(err);
    console.log(result);
  });
  connection.end();
}

async function main() {
  const date = new Date();
  const timestamp = date.getTime();
  const rawData = _.flatten(await Promise.all(_.map(apis, async(api) => {
    return await getData(api, timestamp);
  })));
  const processedData = rawData.map(function(row) {
    return formatData(row);
  }).filter(row => row !== null);
  const uniqueData = _.uniqBy(processedData, 'carpark_id');
  const finalData = _.map(uniqueData, data => _.toArray(data));
  deleteData(date);
  insertData(finalData, timestamp);
}

(async() => await main())();
