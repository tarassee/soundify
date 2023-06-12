CREATE DATABASE IF NOT EXISTS soundify_song_service;

USE soundify_song_service;

CREATE TABLE IF NOT EXISTS song
(
    id          INTEGER NOT NULL AUTO_INCREMENT,
    album       VARCHAR(255),
    artist      VARCHAR(255),
    length      VARCHAR(255),
    name        VARCHAR(255),
    resource_id INTEGER,
    year        VARCHAR(255),
    PRIMARY KEY (id)
) engine = InnoDB;
