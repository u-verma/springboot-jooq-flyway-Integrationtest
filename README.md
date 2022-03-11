## Springboot Flyway Jooq Integration test example

[![](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)



## Authors

- [Umesh Verma] (https://github.com/u-verma)


## Tech Stack

[![](https://img.shields.io/badge/Kotlin-%3E%3D1.6.10-brightgreen)](https://github.com/JetBrains/kotlin/releases/latest)
[![](https://img.shields.io/badge/Java-%3E%3D17-brightgreen)](https://openjdk.java.net/)
[![](https://img.shields.io/badge/Springboot-%3E%3D2.6.4-brightgreen)](https://github.com/spring-projects/spring-boot/releases/latest)
[![](https://img.shields.io/badge/Gradle-%3E%3D7.4-brightgreen)](https://gradle.org/releases/)
[![](https://img.shields.io/badge/Docker-%3E%3D20.10.9-brightgreen)](https://docs.docker.com/engine/release-notes/)

## Installation

### Install services with gradle
```bash
$ git clone 

$ cd ~/{project_root_directory}

$ ./gradlew clean build  

```

### Run tests on local machine
* Run `./gradlew clean test -i`

### Run Project Locally

<details><summary><b><u>Prerequisites</u></b></summary> 

* #### Docker and docker compose must be installed on the machine

</details>

```bash
$ cd ~{project_root_directry}

$ docker-compose up -d

$ gradle bootRun 

```


