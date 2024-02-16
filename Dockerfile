FROM postgres
ENV POSTGRES_USER admin
ENV POSTGRES_PASSWORD admin
ENV POSTGRES_DB spring-jdbc-db

COPY src/main/resources/schema.sql /docker-entrypoint-initdb.d/