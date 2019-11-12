CREATE TABLE IF NOT EXISTS ACCOUNT(
USERID VARCHAR(10) PRIMARY KEY NOT NULL,
PASS VARCHAR(10) NOT NULL,
NAME VARCHAR(20) NOT NULL,
AGE INT(11) NOT NULL);

DROP TABLE IF EXISTS MUTTER;
CREATE TABLE MUTTER(ID INT NOT NULL,
TITLE VARCHAR(255) NOT NULL,
TEXT VARCHAR(255) NOT NULL,
USERID VARCHAR(10) NOT NULL,
DATE_TIME TIMESTAMP);

DROP TABLE IF EXISTS MUTTER_IMAGE;
CREATE TABLE MUTTER_IMAGE(ID INT NOT NULL,
TITLE VARCHAR(255) NOT NULL,
FILENAME VARCHAR(255) NOT NULL,
USERID VARCHAR(10) NOT NULL
);