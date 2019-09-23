### Carina lambda

### Setup of Lambda on AWS
- Install modules
```
npm install
```
- Upload the node_modules and index.js to AWS Lambda
- Replace line with the function that calls main() with the following
```
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
```
- Set AWS Cloudwatch Events to run lamdba hourly with the cron expression
```
0 0/1 * * ? *
```

- To run on local machine
```
npm run lambda
```

### Migrate MongoDB to MySQL
- Run the migration
```
npm run migrate
```

### Resources
- https://docs.aws.amazon.com/systems-manager/latest/userguide/reference-cron-and-rate-expressions.html