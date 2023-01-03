FROM maven:latest

RUN apt-get clean && apt-get update

RUN apt-get install git

RUN git clone https://github.com/PSogeki/Monopoli.git

WORKDIR Monopoli/

RUN mvn clean package

ENV DISPLAY=:0.0

WORKDIR target/

CMD ["java","-jar","monopoli-1.0.jar"]