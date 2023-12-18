## How To Setup & Run
The program main class (RetailerAPIApplication) should execute from your IDE or can be run from the command line. It has been built with Java 8,  but other versions that you choose (8 or above) may be adequate.

## Command Line
The server can be started with 'mvn spring-boot:run' from the command line. (ctl + c to exit) The server can be built with 'mvn clean package' (it will also run the tests where they exist) The tests can be run with 'mvn test'

## Usage
The server will listen on port 8080 and can be seen in the console. (This can be changed in the application.yml file if you need to use a different port)

## cURL Command
* curl --get http://localhost:8080/retailer/v0/products

## Notes
* for massive new item creation, we use factory pattern to load the items from applicaion.yml file