#!/bin/bash
# Docker Run Script for Student Management API
# This script runs the Docker container

echo "Running Student Management API container..."

# Run the Docker container
docker run -d -p 8080:8080 --name student-api student-management-api:latest

if [ $? -eq 0 ]; then
    echo "Container started successfully!"
    echo "API is available at: http://localhost:8080"
    echo "Swagger UI is available at: http://localhost:8080/swagger-ui.html"
    echo "To stop the container, use: docker stop student-api"
    echo "To remove the container, use: docker rm student-api"
else
    echo "Failed to start container!"
    exit 1
fi

