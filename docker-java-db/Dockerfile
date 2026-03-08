FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

RUN mkdir -p /app/lib && \
    wget https://jdbc.postgresql.org/download/postgresql-42.7.5.jar -O /app/lib/postgres.jar

COPY App.java .

RUN javac -cp ".:/app/lib/postgres.jar" App.java

CMD ["java", "-cp", ".:/app/lib/postgres.jar", "App"]