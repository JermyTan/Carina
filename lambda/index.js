const axios = require('axios');
const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');

const url = 'mongodb://ec2-18-216-116-220.us-east-2.compute.amazonaws.com:27017/lta';
const dbName = 'lta';

async function getData(apiUrl) {
  try {
    const response = await axios.get(apiUrl, { "headers": { "AccountKey": "PjPcZIN2SS+LjtXvfFlTIA==", "accept": "application/json" } });
    return response.data.value;
  } catch (error) {
    console.error(error);
    return [];
  }
}

async function getDB() {
  try {
    return await MongoClient.connect(url, { useNewUrlParser: true, useUnifiedTopology: true })
  } catch(err) {
    console.log(err);
    return null;
  }
}

async function main() {
  const client = await getDB();
  console.log('Connected successfully to server');
  const db = client.db(dbName);
  const dbTable = await db.collection('carparkAvailabilityTuesday');

  const timestamp = new Date().getTime();

  const url1 = 'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2';
  const data1 = await getData(url1);
  data1.forEach((entry) => entry.timestamp = timestamp);
  await dbTable.insert(data1);

  const url2 = 'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=500';
  const data2 = await getData(url2);
  data2.forEach((entry) => entry.timestamp = timestamp)
  await dbTable.insert(data2);

  const url3 = 'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=1000';
  const data3 = await getData(url3);
  data3.forEach((entry) => entry.timestamp = timestamp);
  await dbTable.insert(data3);

  const url4 = 'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=1500';
  const data4 = await getData(url4);
  data4.forEach((entry) => entry.timestamp = timestamp);
  await dbTable.insert(data4);

  const url5 = 'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=2000';
  const data5 = await getData(url5);
  data5.forEach((entry) => entry.timestamp = timestamp);
  await dbTable.insert(data5);

  const url6 = 'http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=2500';
  const data6 = await getData(url6);
  data6.forEach((entry) => entry.timestamp = timestamp);
  await dbTable.insert(data6);


  await client.close();
}

exports.handler = async (event) => {
  try {
    await main();
    const response = {
        statusCode: 200,
        body: JSON.stringify('success'),
    };
    return response;
  } catch (err) {
    const response = {
        statusCode: 404,
        body: JSON.stringify(err),
    };
    return response;
  }
};

