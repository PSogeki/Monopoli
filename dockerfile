FROM ubuntu:latest

RUN apt-get clean && apt-get update

RUN apt-get install -y maven

RUN apt-get install -y git

RUN git clone https://github.com/PSogeki/Monopoli.git

WORKDIR Monopoli/

RUN mvn clean package