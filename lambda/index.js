const axios = require('axios');
const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');

const url = 'mongodb://ec2-18-216-116-220.us-east-2.compute.amazonaws.com:27017/carina';
const dbName = 'carina';

async function getData() {
  try {
    const response = await axios.get('http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2', { "headers": { "AccountKey": "PjPcZIN2SS+LjtXvfFlTIA==", "accept": "application/json" } });
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
  const dbTable = await db.collection('carparkAvailability');

  // fetch new data
  const data = await getData();

  // delete old data;
  await dbTable.deleteMany();

  // update data;
  await dbTable.insert(data);
  
  // print data;
  const collection = await dbTable.find({}).toArray();
  console.log(collection);

  client.close();
}

(async function() {
  await main();
})();

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
        statusCode: 200,
        body: JSON.stringify(err),
    };
    return response;
  }
};

