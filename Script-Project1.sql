

--ERS Reimbursement
CREATE TABLE ERS_REIMBURSEMENT(
	REIMB_ID SERIAL PRIMARY KEY,
	REIMB_AMOUNT NUMERIC(20, 2) NOT NULL CHECK (REIMB_AMOUNT > 0),
	REIMB_SUBMITTED TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	REIMB_RESOLVED TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	REIMB_DESCRIPTION VARCHAR(250),
	REIMB_RECEIPT BYTEA
);

--ERS Reimbursement status
CREATE TABLE ERS_REIMBURSEMENT_STATUS(
	REIMB_STATUS_ID INTEGER PRIMARY KEY,
	REIMB_STATUS VARCHAR(10)
);

--ERS Reimbursement type
CREATE TABLE ERS_REIMBURSEMENT_TYPE(
	REIMB_TYPE_ID SERIAL PRIMARY KEY,
	REIMB_TYPE VARCHAR(10) -- LODGING, TRAVEL, FOOD, OR OTHER
);


--ERS User
CREATE TABLE ERS_USERS(
	ERS_USER_ID SERIAL PRIMARY KEY,
	
	ERS_USERNAME VARCHAR(50) NOT NULL UNIQUE,
	ERS_PASSWORD VARCHAR(50) NOT NULL,
	USER_FIRST_NAME VARCHAR(100) NOT NULL,
	USER_LAST_NAME VARCHAR(100) NOT NULL,
	USER_EMAIL VARCHAR(150) NOT NULL UNIQUE
	
);

--ERS USER ROLES
CREATE TABLE ERS_USER_ROLES(
	ERS_USER_ROLE_ID INTEGER PRIMARY KEY,
	USER_ROLE VARCHAR(10)
);


---FOREIGN KEYs---
ALTER TABLE ERS_REIMBURSEMENT ADD COLUMN REIMB_AUTHOR INTEGER REFERENCES ers_users(ERS_USER_ID);
ALTER TABLE ERS_REIMBURSEMENT ADD COLUMN REIMB_RESOLVER INTEGER REFERENCES ERS_USERS(ERS_USER_ID);
ALTER TABLE ERS_REIMBURSEMENT ADD COLUMN REIMB_STATUS_ID INTEGER REFERENCES ERS_REIMBURSEMENT_STATUS(REIMB_STATUS_ID);
ALTER TABLE ERS_REIMBURSEMENT ADD COLUMN REIMB_TYPE_ID INTEGER REFERENCES ERS_REIMBURSEMENT_TYPE(REIMB_TYPE_ID);

ALTER TABLE ERS_USERS ADD COLUMN USER_ROLE_ID INTEGER REFERENCES ERS_USER_ROLES(ERS_USER_ROLE_ID);

--ADD VALUES--
INSERT INTO ERS_USER_ROLES(ERS_USER_ROLE_ID, USER_ROLE) VALUES (1, 'Employee');
INSERT INTO ERS_USER_ROLES(ERS_USER_ROLE_ID, USER_ROLE) VALUES (2, 'Manager');

INSERT INTO ERS_USERS(
	ERS_USERNAME,
	ERS_PASSWORD,
	USER_FIRST_NAME,
	USER_LAST_NAME,
	USER_EMAIL,
	USER_ROLE_ID)
VALUES (
	'employee1',
	'password',
	'James',
	'Bond',
	'employee1@gmail.com',
	1
);

INSERT INTO ERS_USERS(
	ERS_USERNAME,
	ERS_PASSWORD,
	USER_FIRST_NAME,
	USER_LAST_NAME,
	USER_EMAIL,
	USER_ROLE_ID)
VALUES (
	'manager1',
	'password',
	'Arthur',
	'Mondo',
	'manager1@gmail.com',
	2
);

INSERT INTO ERS_REIMBURSEMENT_STATUS(REIMB_STATUS_ID, REIMB_STATUS) VALUES(0, 'Pending');
INSERT INTO ERS_REIMBURSEMENT_STATUS(REIMB_STATUS_ID, REIMB_STATUS) VALUES(1, 'Resolved');
INSERT INTO ERS_REIMBURSEMENT_STATUS(REIMB_STATUS_ID, REIMB_STATUS) VALUES(2, 'Denied');
INSERT INTO ERS_REIMBURSEMENT_TYPE(REIMB_TYPE) VALUES('Lodging');
INSERT INTO ERS_REIMBURSEMENT_TYPE(REIMB_TYPE) VALUES('Travel');
INSERT INTO ERS_REIMBURSEMENT_TYPE(REIMB_TYPE) VALUES('Other');

INSERT INTO ERS_REIMBURSEMENT(	REIMB_AMOUNT, REIMB_DESCRIPTION , REIMB_AUTHOR , REIMB_RESOLVER , REIMB_STATUS_ID , REIMB_TYPE_ID)
VALUES (
	2500.22,
	'Needs to re-imburse my travel fee from Texas to California',
	3,
	4,
	0,
	1
);


