# Build stage
FROM gradle:8.1-alpine
WORKDIR /app
COPY . .