# TODO Application

This project is a TODO application built with Spring Boot and PostgreSQL, containerized using Docker Compose.

## Prerequisites

•   [Docker](https://www.docker.com/get-started) installed on your system.

## Getting Started

Follow these steps to run the application using Docker Compose:

1.  **Clone the repository:**

```git 
  git clone https://github.com/egrudskova/todo-spring
```
```shell
  cd todo-spring
```

2.  Build and run the application:

    Use the following command to build the Docker image and start the application containers

```shell 
  docker-compose up --build
```

3.  Access the application:

    Once the containers are running, you can access the TODO application in your web browser at:
```
http://localhost:8080
```