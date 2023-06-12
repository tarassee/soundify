CREATE DATABASE IF NOT EXISTS soundify_resource;

USE soundify_resource;

CREATE TABLE IF NOT EXISTS audio
(
    id           INTEGER NOT NULL AUTO_INCREMENT,
    format       VARCHAR(255),
    name         VARCHAR(255),
    s3key        VARCHAR(255),
    storage_type VARCHAR(255),
    primary key (id)
) engine = InnoDB;
