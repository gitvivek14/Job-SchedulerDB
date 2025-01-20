# Job Scheduler Application

This is a **Job Scheduler Application** built with **Spring Boot** and **Amazon DynamoDB** 
(local setup). The application allows users to manage job scheduling through a simple REST API. It integrates with **DynamoDB** to 
store and retrieve job data, making it scalable and fast. The application is configured to run locally using **Local DynamoDB** and uses **Java 17**.

---
So far, we have built a Job Scheduler Application using the Spring Reactive Framework and integrated it with AWS DynamoDB (local setup). 
The application provides RESTful APIs for creating a DynamoDB table, adding job details to the table, retrieving job details by ID, and 
listing available DynamoDB tables. We have implemented asynchronous, non-blocking operations using Spring WebFlux to interact with DynamoDB,
ensuring optimal performance and scalability. Additionally, we have set up local DynamoDB for testing and development, and the application runs seamlessly
on Java 17. This setup allows users to easily manage job schedules while leveraging the power of DynamoDB for storage and retrieval.



Setting Up DynamoDB Locally
To set up AWS DynamoDB locally for development, follow these steps:

1. Install Docker (if not already installed)
If you don't have Docker installed, follow these steps:

Download and install Docker from the official website: Docker Installation Guide.
After installation, make sure Docker is running on your machine.
2. Pull DynamoDB Local Docker Image
Run the following command to pull the official DynamoDB local image from Docker Hub:
```bash
docker pull amazon/dynamodb-local
```
3. Run DynamoDB Local in Docker
Run the following command to start DynamoDB locally on your machine:
```bash
docker run -p 8000:8000 amazon/dynamodb-local
```
## Features Implemented

### 1. **DynamoDB Table Creation**
   - The application can create a **DynamoDB table** (`JobScheduler`) using the provided key schema (`id` as the hash key).
   - If the table already exists, the application handles the exception gracefully.

### 2. **Add Job**
   - Users can add a new job to the `JobScheduler` table by providing an **ID** and **Job Name** via a POST request.
   - Jobs are stored as a key-value pair in the table.

### 3. **Get Job by ID**
   - Users can retrieve a specific job by its **ID**.
   - If the job is found, the job name is returned. Otherwise, an error message is displayed.

### 4. **List DynamoDB Tables**
   - The application allows you to list all DynamoDB tables available in the local setup.

### 5. **Fetch All Jobs**
   - This feature retrieves all jobs stored in the `JobScheduler` table and returns the list of job names.

---

## Requirements

- **JDK 17**
- **Maven** (to build and run the project)
- **Local DynamoDB** (can be set up using Docker or directly running the DynamoDB Local JAR)
- **Postman** (for API testing)

---

## Setup Instructions

### 1. **Clone the Repository**

```bash
git clone https://github.com/your-username/job-scheduler.git
cd job-scheduler
```
2. Build the Project
Ensure that you have JDK 17 installed on your machine. Build the project using Maven:
```bash
mvn clean install
```
3. Run DynamoDB Locally
If you don’t have DynamoDB Local set up, you can run it using Docker:
```bash
docker run -p 8000:8000 amazon/dynamodb-local
```
4. Run the Spring Boot Application
Once the DynamoDB local server is running, you can start the Spring Boot application:
```bash
mvn spring-boot:run
```
The application will run on http://localhost:8080

API Endpoints and Testing
Here are the Postman API requests to test the application:

1. Create DynamoDB Table
Endpoint: POST /jobs/create
Description: Creates a table in DynamoDB if it doesn't already exist.
Postman Command:
```
bash
POST http://localhost:8080/jobs/create
```
3. Add Job
Endpoint: POST /jobs/add
Description: Adds a job to the JobScheduler table.
Parameters:
id (String): The unique ID for the job.
jobName (String): The name of the job.
Postman Command:
```
bash

POST http://localhost:8080/jobs/add?id=1&jobName=Sample%20Job
```
4. Get Job by ID
Endpoint: GET /jobs/get
Description: Retrieves a job by its ID.
Parameters:
id (String): The ID of the job.
Postman Command:
```
bash
GET http://localhost:8080/jobs/get?id=1
```
5. List All DynamoDB Tables
Endpoint: GET /jobs/listTables
Description: Lists all DynamoDB tables.
Postman Command:
```
bash
GET http://localhost:8080/jobs/listTables
```
6. Get All Jobs
Endpoint: GET /jobs/getAllJobs
Description: Fetches all jobs stored in the JobScheduler table.
Postman Command:
```
bash
GET http://localhost:8080/jobs/getAllJobs
```

