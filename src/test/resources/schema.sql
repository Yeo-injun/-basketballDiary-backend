CREATE TABLE USER(
    USER_SEQ int PRIMARY KEY AUTO_INCREMENT,
    USER_ID varchar(30) NOT NULL,
    NAME varchar(200) ,
    PWD varchar(200) ,
    BIRTH_YMD varchar(8) ,
    EMAIL varchar(45) NOT NULL,
    USER_IMAGE_PATH varchar(200) ,
    REG_DATE date,
    UPDATE_DATE date,
    USER_REG_YN varchar(1) NOT NULL,
    GENDER varchar(2) NOT NULL,
    HEIGHT float NOT NULL,
    WEIGHT float,
    SIDO_CODE varchar(5),
    SIGUNGU_CODE varchar(5),
    POSITION_CODE varchar(2),
    ROADADDRESS varchar(100),
    INS_USER varchar(60),
    INS_TIME timestamp,
    MOD_USER varchar(60),
    MOD_TIME timestamp
);