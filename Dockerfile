FROM openjdk:11-jdk-slim
COPY data/* /tmp/
COPY ./target/dataeng-challenge-1.0.jar game-ranks.jar
ENTRYPOINT ["java","-jar","game-ranks.jar"]