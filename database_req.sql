CREATE DATABASE IF NOT EXISTS TennisClubDB;
USE TennisClubDB;

-- Users Table
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(50),
    status VARCHAR(50)
);

-- Member Profiles Table
CREATE TABLE MemberProfiles (
    user_id INT PRIMARY KEY,
    phone_number VARCHAR(15),
    address TEXT,
    guest_passes_remaining INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- Courts Table
CREATE TABLE Courts (
    court_number INT PRIMARY KEY AUTO_INCREMENT,
    availability_status VARCHAR(50) NOT NULL
);

-- Court Reservations Table
CREATE TABLE CourtReservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    court_number INT NOT NULL,
    reservation_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (court_number) REFERENCES Courts(court_number) ON DELETE CASCADE,
    INDEX (reservation_date, court_number)
);

-- Financial Transactions Table
CREATE TABLE FinancialTransactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    transaction_type VARCHAR(20) CHECK (transaction_type IN ('PAYMENT', 'REFUND', 'ADJUSTMENT')),
    status VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE SET NULL
);

-- Payment Disputes Table
CREATE TABLE PaymentDisputes (
    dispute_id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id INT NOT NULL,
    reason TEXT NOT NULL,
    status VARCHAR(50),
    FOREIGN KEY (transaction_id) REFERENCES FinancialTransactions(transaction_id) ON DELETE CASCADE
);

-- Financial Reports Table
CREATE TABLE FinancialReports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    generated_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    report_content TEXT NOT NULL
);

-- Reconciliations Table
CREATE TABLE Reconciliations (
    reconciliation_id INT AUTO_INCREMENT PRIMARY KEY,
    discrepancies TEXT NOT NULL
);

-- Report Criteria Table
CREATE TABLE ReportCriteria (
    criteria_id INT AUTO_INCREMENT PRIMARY KEY,
    parameter_name VARCHAR(255) NOT NULL,
    parameter_value TEXT NOT NULL
);

-- Events Table
CREATE TABLE Events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    event_date DATE NOT NULL,
    event_time TIME NOT NULL,
    location VARCHAR(255) NOT NULL
);

-- Audit Logs Table
CREATE TABLE AuditLogs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    event_details TEXT NOT NULL
);

INSERT INTO Users (username, password_hash, email, role, status)
VALUES ('john_smith', 'tennis123', 'john@gmail.com', 'member', 'active');

INSERT INTO Courts (availability_status) VALUES ('Available');

INSERT INTO CourtReservations (user_id, court_number, reservation_date, start_time, end_time)
VALUES (1, 1, '2025-04-01', '10:00:00', '11:00:00');
