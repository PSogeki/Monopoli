FROM ubuntu:latest

LABEL mantainer="Paolo Di Biase <p.dibiase4@studenti.unimol.it>"

RUN apt-get clean && apt-get update

RUN apt-get install -y maven

RUN apt-get install -y git

RUN apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN git clone https://github.com/PSogeki/Monopoli.git

WORKDIR Monopoli/

RUN mvn clean deploy