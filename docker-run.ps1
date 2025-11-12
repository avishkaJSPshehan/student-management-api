# Docker Run Script for Student Management API
# This script runs the Docker container

Write-Host "Running Student Management API container..." -ForegroundColor Green

# Run the Docker container
docker run -d -p 8080:8080 --name student-api student-management-api:latest

if ($LASTEXITCODE -eq 0) {
    Write-Host "Container started successfully!" -ForegroundColor Green
    Write-Host "API is available at: http://localhost:8080" -ForegroundColor Cyan
    Write-Host "Swagger UI is available at: http://localhost:8080/swagger-ui.html" -ForegroundColor Cyan
    Write-Host "To stop the container, use: docker stop student-api" -ForegroundColor Yellow
    Write-Host "To remove the container, use: docker rm student-api" -ForegroundColor Yellow
} else {
    Write-Host "Failed to start container!" -ForegroundColor Red
    exit 1
}

