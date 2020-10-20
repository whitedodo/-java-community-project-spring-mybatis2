-- Oracle 11 - 자동번호 생성 테이블 정의
-- Table 생성 (BOARD)
-- NEW.ID (Table의 id를 가리킴)
CREATE TABLE board
(
    id NUMBER PRIMARY KEY,
    name VARCHAR2(30),
    subject VARCHAR2(30),
    memo NCLOB,
    count NUMBER,
    regidate TIMESTAMP,
    ip VARCHAR(128)
    
);

-- Sequence 정의
CREATE SEQUENCE board_sequence
START WITH 1
INCREMENT BY 1;

-- Trigger 생성
-- BEFORE INSERT on '테이블명'
CREATE OR REPLACE TRIGGER board_trigger
BEFORE INSERT
    ON board
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT board_sequence.nextval INTO :NEW.ID FROM dual;
END;

-- Oracle 11 - 자동번호 생성 테이블 정의
-- Table 생성 (Member)
-- NEW.ID (Table의 id를 가리킴)
CREATE TABLE communities_member
(
    idx NUMBER PRIMARY KEY,
    username VARCHAR2(200),
    passwd VARCHAR2(200),
    regidate TIMESTAMP,
    ip VARCHAR(128)
    
);

-- Sequence 정의
CREATE SEQUENCE communities_member_sequence
START WITH 1
INCREMENT BY 1;

-- Trigger 생성
-- BEFORE INSERT on '테이블명'
CREATE OR REPLACE TRIGGER communities_member_trigger
BEFORE INSERT
    ON communities_member
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT communities_member_sequence.nextval INTO :NEW.IDX FROM dual;
END;

-- Oracle 11 - 자동번호 생성 테이블 정의
-- Table 생성 (BOARD_FILE)
-- NEW.ID (Table의 id를 가리킴)
CREATE TABLE board_file
(
    idx NUMBER PRIMARY KEY,
    realname VARCHAR2(200),
    filedir VARCHAR2(200),
    filename VARCHAR2(200),
	fileext VARCHAR2(30),
    contenttype VARCHAR2(200),
    filesize NUMBER(20),
    board_id NUMBER,
    CONSTRAINT FK_TOPICS FOREIGN KEY(board_id)
    REFERENCES board_sample(id),
    regidate DATE
);

-- Sequence 정의
CREATE SEQUENCE board_file_sequence
START WITH 1
INCREMENT BY 1;

-- Trigger 생성
-- BEFORE INSERT on '테이블명'
CREATE OR REPLACE TRIGGER board_file_trigger
BEFORE INSERT
    ON board_file
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT board_file_sequence.nextval INTO :NEW.IDX FROM dual;
END;


-- Oracle 11 - 자동번호 생성 테이블 정의
-- Table 생성 (BOARD_FILE)
-- NEW.ID (Table의 id를 가리킴)
CREATE TABLE board_file_sample
(
    idx NUMBER PRIMARY KEY,
    realname VARCHAR2(200),
    filedir VARCHAR2(200),
    filename VARCHAR2(200),
	fileext VARCHAR2(30),
    contenttype VARCHAR2(200),
    filesize NUMBER(20),
    board_id NUMBER,
    CONSTRAINT FK_TOPICS FOREIGN KEY(board_id)
    REFERENCES board_sample(id),
    regidate DATE
);

-- Sequence 정의
CREATE SEQUENCE board_file_sample_sequence
START WITH 1
INCREMENT BY 1;

-- Trigger 생성
-- BEFORE INSERT on '테이블명'
CREATE OR REPLACE TRIGGER board_file_sample_trigger
BEFORE INSERT
    ON board_file_sample
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT board_file_sample_sequence.nextval INTO :NEW.IDX FROM dual;
END;