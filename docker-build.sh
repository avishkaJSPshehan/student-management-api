#!/bin/bash
# Docker Build Script for Student Management API
# This script builds the Docker image for the application

echo "Building Docker image for Student Management API..."

# Build the Docker image
docker build -t student-management-api:latest .

if [ $? -eq 0 ]; then
    echo "Docker image built successfully!"
    echo "To run the container, use: docker run -p 8080:8080 student-management-api:latest"
else
    echo "Docker build failed!"
    exit 1
fi

