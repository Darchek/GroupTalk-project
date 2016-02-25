drop database if exists grouptalkdb;
create database grouptalkdb;

use grouptalkdb;

CREATE TABLE users (
    id BINARY(16) NOT NULL,
    loginid VARCHAR(15) NOT NULL UNIQUE,
    password BINARY(16) NOT NULL,
    email VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    userid BINARY(16) NOT NULL,
    role ENUM ('registered', 'administrator'),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (userid, role)
);

CREATE TABLE auth_tokens (
    userid BINARY(16) NOT NULL,
    token BINARY(16) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (token)
);

CREATE TABLE groups (
    id BINARY(16) NOT NULL,
    name VARCHAR(100) NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    creation_timestamp DATETIME not null default current_timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE themes (
    id BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(500) NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    creation_timestamp DATETIME not null default current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade
);

CREATE TABLE comments (
    id BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,
    themeid BINARY(16) NOT NULL,
    answer VARCHAR(500) NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    creation_timestamp DATETIME not null default current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    FOREIGN KEY (themeid) REFERENCES themes(id) on delete cascade
);

CREATE TABLE group_user (
    groupid BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,
    FOREIGN KEY (groupid) REFERENCES groups(id) on delete cascade,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (groupid, userid)
);