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
    id BINARY(16) NOT NULL,e
    userid BINARY(16) NOT NULL,
    groupid BINARY(16) NOT NULL,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(500) NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    creation_timestamp DATETIME not null default current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    FOREIGN KEY (groupid) REFERENCES groups(id) on delete cascade
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

-- To make it normal with UNHEX value in id and MD5 in the password too.
-- INSERT INTO users (id, loginid, password, email, fullname) values (UNHEX(REPLACE(UUID(),'-','')) , "admin", UNHEX(MD5("admin")), "admin@gmail.com", "Administrador Marti");
-- SELECT @id:= id from users where loginid="admin";
-- INSERT INTO user_roles (userid, role) values (@id, 'administrator');

-- select * from groups JOIN group_user ON groups.id = group_user.groupid WHERE groups.id = unhex("02340000000000000000000000000000");
-- To make proves before

INSERT INTO users (id, loginid, password, email, fullname) values (unhex("1234"), "admin", "passadmin", "admin@gmail.com", "Administrador Marti");
INSERT INTO users (id, loginid, password, email, fullname) values (unhex("12345"), "prova", "passprova", "prova@gmail.com", "Senyor Prova");
INSERT INTO users (id, loginid, password, email, fullname) values (unhex("123456"), "prova2", "passprova2", "prova2@gmail.com", "Senyor Prova2");
INSERT INTO user_roles (userid, role) values (unhex("1234"), "administrator");
INSERT INTO user_roles (userid, role) values (unhex("12345"), "administrator");
INSERT INTO user_roles (userid, role) values (unhex("123456"), "administrator");

INSERT INTO groups (id, name) values (unhex("234"), "provaGroup");
INSERT INTO groups (id, name) values (unhex("245"), "provaGroup2");
INSERT INTO groups (id, name) values (unhex("256"), "provaGroup3");
INSERT INTO group_user (groupid, userid) values (unhex("234"), unhex("1234"));
INSERT INTO group_user (groupid, userid) values (unhex("234"), unhex("123456"));
INSERT INTO group_user (groupid, userid) values (unhex("245"), unhex("1234"));
INSERT INTO group_user (groupid, userid) values (unhex("245"), unhex("12345"));
INSERT INTO group_user (groupid, userid) values (unhex("256"), unhex("12345"));
INSERT INTO group_user (groupid, userid) values (unhex("256"), unhex("123456"));

INSERT INTO themes (id, userid, groupid, title, content) values (unhex("37"), unhex("1234"), unhex("234"), "ProveTheme", "Una prova de tema");
INSERT INTO themes (id, userid, groupid, title, content) values (unhex("38"), unhex("12345"), unhex("245"), "ProveTheme2", "Una prova de tema 2");
INSERT INTO themes (id, userid, groupid, title, content) values (unhex("39"), unhex("123456"), unhex("256"), "ProveTheme3", "Una prova de tema 3");

INSERT INTO comments (id, userid, themeid, answer) values (unhex("0000101"), unhex("1234"), unhex("37"), "ProveComment 1a");
INSERT INTO comments (id, userid, themeid, answer) values (unhex("0000102"), unhex("1234"), unhex("38"), "ProveComment 1b");
INSERT INTO comments (id, userid, themeid, answer) values (unhex("0000103"), unhex("1234"), unhex("39"), "ProveComment 1c");
INSERT INTO comments (id, userid, themeid, answer) values (unhex("0000201"), unhex("12345"), unhex("37"), "ProveComment 2a");
INSERT INTO comments (id, userid, themeid, answer) values (unhex("0000202"), unhex("12345"), unhex("38"), "ProveComment 2b");
INSERT INTO comments (id, userid, themeid, answer) values (unhex("0000203"), unhex("12345"), unhex("39"), "ProveComment 2c");
INSERT INTO comments (id, userid, themeid, answer) values (unhex("0000301"), unhex("123456"), unhex("37"), "ProveComment 3a");
INSERT INTO comments (id, userid, themeid, answer) values (unhex("0000302"), unhex("123456"), unhex("38"), "ProveComment 3b");
INSERT INTO comments (id, userid, themeid, answer) values (unhex("0000303"), unhex("123456"), unhex("39"), "ProveComment 4c");


-- select groups.id, groups.name, groups.last_modified, groups.creation_timestamp, group_user.userid, themes.id, themes.userid, themes.title, themes.content, themes.last_modified
-- , themes.creation_timestamp from groups join group_user ON groups.id = group_user.groupid JOIN themes ON groups.id = themes.groupid where groups.id = unhex("0234000000000000000000000
--  0000000");
-- unhex("02340000000000000000000000000000");
