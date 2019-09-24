const mysql = require('mysql');
const axios = require('axios');
const csv = require('csvtojson');
const fs = require('fs');
const dateFns = require('date-fns');
const _ = require('lodash');

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

function insertData(data) {
  const connection = getConnection();
  connection.connect();
  const SQL_COMMAND = 'INSERT INTO carpark_availability_saturday VALUES ?';
  const query = connection.query(SQL_COMMAND, [data], function(err, result) {
    console.log(err);
    console.log(result);
  });
  connection.end();
}

async function main() {
  const rawData = await readData('saturday.csv');
  const processedData = rawData.map(function(row) {
    return formatData(row);
  }).filter(row => row !== null);
  const uniqueData = _.uniqBy(processedData, 'carpark_id');
  const finalData = _.map(uniqueData, data => _.toArray(data));

  insertData(finalData);
  console.log(finalData);

}

(async() => await main())();
