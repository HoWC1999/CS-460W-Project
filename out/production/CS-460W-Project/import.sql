CREATE TABLE IF NOT EXISTS audit_logs (
                                        log_id INT AUTO_INCREMENT PRIMARY KEY,
                                        event_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        event_details CLOB
);

CREATE TABLE IF NOT EXISTS courts (
                                    court_number INT NOT NULL PRIMARY KEY,
                                    is_available BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS criteria_filters (
                                              criteria_id INT NOT NULL,
                                              filter_value VARCHAR(255),
                                              filter_key VARCHAR(255) NOT NULL,
                                              PRIMARY KEY (criteria_id, filter_key)
);

CREATE TABLE IF NOT EXISTS events (
                                    event_id INT AUTO_INCREMENT PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
                                    description CLOB,
                                    event_date DATE NOT NULL,
                                    event_time TIME NOT NULL,
                                    location VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS financial_reports (
                                               report_id INT AUTO_INCREMENT PRIMARY KEY,
                                               generated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                               report_data CLOB
);

CREATE TABLE IF NOT EXISTS reconciliations (
                                             reconciliation_id INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS reconciliation_discrepancies (
                                                          reconciliation_id INT NOT NULL,
                                                          discrepancy VARCHAR(255),
                                                          CONSTRAINT FK_recon_discrepancies FOREIGN KEY (reconciliation_id)
                                                            REFERENCES reconciliations(reconciliation_id)
);

CREATE TABLE IF NOT EXISTS report_criteria (
                                             criteria_id INT AUTO_INCREMENT PRIMARY KEY,
                                             start_date DATE,
                                             end_date DATE
);

-- Note: criteria_filters is defined above; if you need a separate instance with a foreign key on report_criteria, you can rename it.
ALTER TABLE criteria_filters
  ADD CONSTRAINT FK_criteria_filters FOREIGN KEY (criteria_id)
    REFERENCES report_criteria(criteria_id);

CREATE TABLE IF NOT EXISTS users (
                                   user_id INT AUTO_INCREMENT PRIMARY KEY,
                                   username VARCHAR(50) NOT NULL UNIQUE,
                                   password_hash VARCHAR(255) NOT NULL,
                                   email VARCHAR(100) NOT NULL UNIQUE,
                                   role VARCHAR(20) NOT NULL,
                                   status VARCHAR(20) DEFAULT 'Active' NOT NULL,
                                   phone_number VARCHAR(12),
                                   full_name VARCHAR(50) UNIQUE,
                                   address VARCHAR(255),
                                   billing_plan VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS billing_transactions (
                                                  billing_id INT AUTO_INCREMENT PRIMARY KEY,
                                                  amount DECIMAL(10, 2) NOT NULL,
                                                  billing_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                                  description VARCHAR(255),
                                                  fee_type VARCHAR(50) NOT NULL,
                                                  user_id INT NOT NULL,
                                                  CONSTRAINT FK_billing_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS court_reservations (
                                                reservation_id INT AUTO_INCREMENT PRIMARY KEY,
                                                reservation_date DATE NOT NULL,
                                                start_time TIME NOT NULL,
                                                end_time TIME NOT NULL,
                                                user_id INT NOT NULL,
                                                court_number INT NOT NULL,
                                                CONSTRAINT FK_reservation_court FOREIGN KEY (court_number) REFERENCES courts(court_number),
                                                CONSTRAINT FK_reservation_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS member_profiles (
                                             profile_id INT AUTO_INCREMENT PRIMARY KEY,
                                             address VARCHAR(255),
                                             guest_passes_remaining INT DEFAULT 0,
                                             phone_number VARCHAR(255),
                                             user_id INT NOT NULL UNIQUE,
                                             CONSTRAINT FK_member_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS transactions (
                                          transaction_id INT AUTO_INCREMENT PRIMARY KEY,
                                          user_id INT NOT NULL,
                                          amount DECIMAL(10,2) NOT NULL,
                                          transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                          transaction_type VARCHAR(50) NOT NULL,
                                          status VARCHAR(20) NOT NULL,
                                          description VARCHAR(255),
                                          CONSTRAINT FK_transaction_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payment_disputes (
                                              dispute_id INT AUTO_INCREMENT PRIMARY KEY,
                                              reason VARCHAR(255),
                                              status VARCHAR(255) NOT NULL,
                                              transaction_id INT NOT NULL,
                                              CONSTRAINT FK_dispute_transaction FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)
);

CREATE TABLE IF NOT EXISTS guest_passes (
                                          guest_pass_id INT AUTO_INCREMENT PRIMARY KEY,
                                          user_id INT NOT NULL,
                                          price DECIMAL(10,2) NOT NULL,
                                          purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                          expiration_date TIMESTAMP NOT NULL,
                                          used BOOLEAN NOT NULL,
                                          CONSTRAINT FK_guest_pass_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
INSERT INTO users (user_id, address, email, full_name, password_hash, phone_number, role, status, username, billing_plan) VALUES (1, '123 Main St', 'member1@test.com', 'Member One', 'password1', '1111111111', 'MEMBER', 'Active', 'member1', null);
INSERT INTO users (user_id, address, email, full_name, password_hash, phone_number, role, status, username, billing_plan) VALUES (2, '456 Bank Rd', 'treasurer1@test.com', 'Treasurer One', 'password2', '2222222222', 'TREASURER', 'Active', 'treasurer1', null);
INSERT INTO users (user_id, address, email, full_name, password_hash, phone_number, role, status, username, billing_plan) VALUES (3, '789 Admin Ave', 'admin1@test.com', 'Admin One', 'password3', '3333333333', 'ADMIN', 'Active', 'admin1', null);
INSERT INTO users (user_id, address, email, full_name, password_hash, phone_number, role, status, username, billing_plan) VALUES (4, null, 'tes@test.tes', null, '$2a$10$Aq3Kr31VC/wcwNo0VLenpOZn4SIVJqn6j8.IUNQKLwXPJpdZGo95q', '1111111111', 'MEMBER', 'Active', 'tes', null);
INSERT INTO users (user_id, address, email, full_name, password_hash, phone_number, role, status, username, billing_plan) VALUES (5, null, 'taaa@example.com', null, '$2a$10$Tqv7Lrq47DrEKAJGCUIwEOKnPkc8ur1dcQVkaR6T.j7yioKsFMe2C', '2323234444', 'ADMIN', 'Active', 'tess', 'MONTHLY');
INSERT INTO users (user_id, address, email, full_name, password_hash, phone_number, role, status, username, billing_plan) VALUES (6, null, 'afletcher123456@gmail.com', null, '$2a$10$guCddsMVaZYmeGJNXkEKwOzWeX6QK/5NTzU/mII60ExaKrcYnbMay', '(732) 503-2431', 'MEMBER', 'Active', 'test', 'MONTHLY');
