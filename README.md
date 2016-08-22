# ocr-participants
[![CircleCI](https://circleci.com/gh/ryanjbaxter/ocr-participants.svg?style=svg)](https://circleci.com/gh/ryanjbaxter/ocr-participants)

Repo for OCR Participants service from my [blog](http://ryanjbaxter.com).

## Running
This is a simple Spring Boot app which can be run easily just by cloning the repo.

```
$ git clone https://github.com/ryanjbaxter/ocr-participants
$ cd ocr-participants
$ ./mvnw spring-boot:run
```

Alternatively you can run this application using an [image](https://hub.docker.com/r/ryanjbaxter/ocr-participants/) from Docker Hub.

```
$ docker run -i -p 8383:8383 ryanjbaxter/ocr-participants
```

## Usage
This app provides participant information for various races.

```
$ curl http://localhost:8181/participants
$ curl http://localhost:8181/participants/raceId
```
