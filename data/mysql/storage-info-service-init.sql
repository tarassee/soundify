CREATE DATABASE IF NOT EXISTS soundify_storage_info_service;

USE soundify_storage_info_service;

CREATE TABLE IF NOT EXISTS storage_info
(
    id           INTEGER NOT NULL AUTO_INCREMENT,
    bucket       VARCHAR(255),
    path         VARCHAR(255),
    storage_type VARCHAR(255),
    PRIMARY KEY (id)
) engine = InnoDB;

INSERT INTO storage_info (bucket, path, storage_type)
VALUES ('soundify-staged-storage-bucket', '/', 'STAGING'),
       ('soundify-permanent-storage-bucket', '/', 'PERMANENT');
