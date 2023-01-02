FROM maven:latest

RUN apt-get install git

RUN git clone https://github.com/PSogeki/Monopoli.git

WORKDIR Monopoli/

RUN mvn clean package

WORKDIR target/

CMD ["java","-jar","monopoli-1.0-SNAPSHOT.jar"]