CREATE TABLE USER (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    USERNAME VARCHAR (250) NOT NULL,
    PASSWORD VARCHAR (250) NOT NULL,
    EMAIL VARCHAR (250) NOT NULL,
    FIRST_NAME VARCHAR (250) NOT NULL,
    LAST_NAME VARCHAR (250) NOT NULL,
    LOL_USERNAME VARCHAR (250) NOT NULL,
    LOL_SERVER VARCHAR (250) NOT NULL,
    ACTIVATION_ID VARCHAR (250),
    CODE_NUMBER VARCHAR (250),
    ACTIVATED BOOLEAN NOT NULL DEFAULT 0
);
