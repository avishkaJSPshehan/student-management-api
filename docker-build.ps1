# Docker Build Script for Student Management API
# This script builds the Docker image for the application

Write-Host "Building Docker image for Student Management API..." -ForegroundColor Green

# Build the Docker image
docker build -t student-management-api:latest .

if ($LASTEXITCODE -eq 0) {
    Write-Host "Docker image built successfully!" -ForegroundColor Green
    Write-Host "To run the container, use: docker run -p 8080:8080 student-management-api:latest" -ForegroundColor Cyan
} else {
    Write-Host "Docker build failed!" -ForegroundColor Red
    exit 1
}

