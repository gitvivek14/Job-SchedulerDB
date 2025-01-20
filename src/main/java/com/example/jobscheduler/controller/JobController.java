package com.example.jobscheduler.controller;

import com.example.jobscheduler.service.DynamoDbService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final DynamoDbService dynamoDbService;

    @Autowired
    public JobController(DynamoDbService dynamoDbService) {
        this.dynamoDbService = dynamoDbService;
    }

    // Endpoint to create DynamoDB table
    @PostMapping("/create")
    public String createTable() {
        dynamoDbService.createTable();
        return "Table created or already exists in DynamoDB";
    }

    // Endpoint to add a job to DynamoDB
    @PostMapping("/add")
    public String addJob(@RequestParam String id, @RequestParam String jobName) {
        dynamoDbService.putItem(id, jobName);
        return "Job added to DynamoDB";
    }
    @GetMapping("/get")
    public String getJob(@RequestParam String id) {
        return dynamoDbService.getItem(id);
    }
    @GetMapping("/listTables")
    public List<String> listTables() {
        return dynamoDbService.listTables();
    }
    @GetMapping("/getAllJobs")
public List<String> getAllJobs() {
    return dynamoDbService.getAllJobs();
}

}
