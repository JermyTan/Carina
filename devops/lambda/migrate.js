const mysql = require('mysql2/promise');
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

async function getConnection() {
  return await mysql.createConnection(dbConfig);
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
  const latitude = loc[0];
  const longitude = loc[1];

  const date = getDate(timestamp);
  const day = getNameOfDay(date);
  const hour = _.toString(getHourOfDay(date));

  const available_lots = AvailableLots;

  const hasNaN = _.isNaN(latitude) || _.isNaN(longitude) || _.isNaN(available_lots);

  const data = {
    carpark_id: CarParkID,
    area: Area,
    development: Development,
    latitude,
    longitude,
    available_lots: AvailableLots,
    lot_type: LotType,
    agency: Agency,
    hour,
    timestamp
  }

  if (hasNaN) {
    return null;
  }
  return data;
}

async function readData(csvFilePath) {
  return await csv().fromFile(csvFilePath);
}

async function main() {
  const rawData = await readData('friday.csv');
  const processedData = rawData.map(function(row) {
    return formatData(row);
  }).filter(row => row !== null);
  const connection = await getConnection();
  await Promise.all(processedData.map(async (data) => {
    const result = await connection.query('INSERT INTO carpark_availability_friday SET ?', data);
  }));

  await connection.end();

}

(async() => await main())();
