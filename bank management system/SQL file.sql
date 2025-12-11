-- CREATE TABLES FOR BANK MANAGEMENT SYSTEM

-- If u want to dlt the previous table (otherwise don't use it x_x)
DROP TABLE IF EXISTS bank;
DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS signup3;
DROP TABLE IF EXISTS signup2;
DROP TABLE IF EXISTS signup;


CREATE DATABASE IF NOT EXISTS bankmanagementsystem;
USE bankmanagementsystem;

-- Table 1: signup (for Signup.java - Page 1)
CREATE TABLE signup (
    formno VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100),
    fname VARCHAR(100),
    dob VARCHAR(20),
    gender VARCHAR(10),
    email VARCHAR(100),
    marital VARCHAR(20),
    address VARCHAR(200),
    city VARCHAR(50),
    zipcode VARCHAR(10),
    state VARCHAR(50)
);

-- Table 2: signup2 (for Signup2.java - Page 2)
CREATE TABLE signup2 (
    formno VARCHAR(20) PRIMARY KEY,
    religion VARCHAR(50),
    income VARCHAR(50),
    education VARCHAR(50),
    occupation VARCHAR(50),
    nid VARCHAR(20),
    phone VARCHAR(20),
    scitizen VARCHAR(5),
    eaccount VARCHAR(5),
    permanent_address VARCHAR(255)
);

-- Table 3: signup3 (for Signup3.java - Page 3)
CREATE TABLE signup3 (
    formno VARCHAR(20) PRIMARY KEY,
    account_type VARCHAR(50),
    cardno VARCHAR(20),
    pin VARCHAR(10),
    facility VARCHAR(200)
);

-- Table 4: login (for Login.java)
CREATE TABLE login (
    formno VARCHAR(20),
    cardno VARCHAR(20),
    pin VARCHAR(10)
);

-- Table 5: bank (for Deposit.java, Withdrawl.java, BalanceEnquiry.java)
CREATE TABLE bank (
    pin VARCHAR(20),
    date VARCHAR(50),
    mode VARCHAR(50),
    amount VARCHAR(50)
);


-- 11. CHECK THE DATA
SELECT * FROM signup;
SELECT * FROM signup2;
SELECT * FROM signup3;
SELECT * FROM login;
SELECT * FROM bank;


