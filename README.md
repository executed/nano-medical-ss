# Nano Medical Web Application

## Overview

Nano medical is web application built for use in some kind of medical centers which need to have the web system of appointments:

1. Serve as an open-source, public-facing reference.      
2. Help to understand the front-backend connectivity and can be used as fast start in some kind of web applications.

## Check Out and Build from Source

1. Clone the repository from GitHub:

		$ git clone git://https://bitbucket.org/executed/medical-center-ss.git

2. Navigate into the cloned repository directory:

		$ cd medical-center-ss

3. The project uses [Gradle](https://gradle.org) to build:

		$ gradle clean build 
		
4. The project uses [Postgresql](https://www.postgresql.org) as database:

        Navigate and restore database (psql is recommended) 
        $ /src/main/resources/sql/medical_center.sql

        Navigate and change default configurations up to yours
		$ /src/main/resources/properties/configuration.properties

5. Navigate and move war archive to your Tomcat folder (by default it uses sources from ~/webapps directory)

		$ /build/libs/

2. Access the deployed webapp at 

		http://localhost:8080/'name_of_war_archive'

## IDE Support

If you would like to build and run from a Gradle/Java Dynamic Web-project-capable IDE, such as Intellij IDEA Tool Suite, you may simply import "as a Gradle Project" into your IDE and deploy the project to your IDE's embedded servlet container.