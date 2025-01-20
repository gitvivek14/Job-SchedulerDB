package com.example.jobscheduler.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DynamoDbService {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDbService() {
        this.dynamoDbClient = DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8000"))  // Local DynamoDB endpoint
                .region(Region.US_WEST_2)  // Use your preferred region (region doesn't matter for local DynamoDB)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("fakeAccessKey", "fakeSecretKey")))  // Use fake credentials
                .build();
    }

    // Create a DynamoDB table (if not already exists)
    public void createTable() {
        CreateTableRequest createTableRequest = CreateTableRequest.builder()
                .tableName("JobScheduler")
                .keySchema(KeySchemaElement.builder().attributeName("id").keyType(KeyType.HASH).build())
                .attributeDefinitions(
                        AttributeDefinition.builder().attributeName("id").attributeType(ScalarAttributeType.S).build()
                )
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(5L).writeCapacityUnits(5L).build())
                .build();

        try {
            dynamoDbClient.createTable(createTableRequest);
            System.out.println("Table created successfully.");
        } catch (ResourceInUseException e) {
            System.out.println("Table already exists.");
        } catch (DynamoDbException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    // Add a job to the DynamoDB table
    public void putItem(String id, String jobName) {
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName("JobScheduler")
                .item(Map.of(
                        "id", AttributeValue.builder().s(id).build(),
                        "jobName", AttributeValue.builder().s(jobName).build()
                ))
                .build();

        try {
            dynamoDbClient.putItem(putItemRequest);
            System.out.println("Job added successfully to DynamoDB.");
        } catch (DynamoDbException e) {
            System.out.println("Error adding job to DynamoDB: " + e.getMessage());
        }
    }
    public String getItem(String id) {
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("JobScheduler")
                .key(Map.of(
                        "id", AttributeValue.builder().s(id).build()
                ))
                .build();

        try {
            GetItemResponse response = dynamoDbClient.getItem(getItemRequest);
            if (response.hasItem()) {
                // Extracting the job name from the retrieved item
                String jobName = response.item().get("jobName").s();
                return "Job found: " + jobName;
            } else {
                return "Job not found for ID: " + id;
            }
        } catch (Exception e) {
            return "Error fetching job: " + e.getMessage();
        }
    }
    public List<String> listTables() {
        ListTablesRequest listTablesRequest = ListTablesRequest.builder()
                .build();

        try {
            ListTablesResponse response = dynamoDbClient.listTables(listTablesRequest);
            return response.tableNames();
        } catch (Exception e) {
            System.out.println("Error listing tables: " + e.getMessage());
            return null;
        }
    }
    public List<String> getAllJobs() {
    ScanRequest scanRequest = ScanRequest.builder()
            .tableName("JobScheduler")
            .build();

    try {
        ScanResponse response = dynamoDbClient.scan(scanRequest);
        List<String> jobs = new ArrayList<>();
        
        // Iterate through the response and extract job names
        response.items().forEach(item -> {
            String jobName = item.get("jobName").s();
            jobs.add(jobName);
        });
        
        return jobs;
    } catch (Exception e) {
        System.out.println("Error fetching all jobs: " + e.getMessage());
        return null;
    }
}


}
