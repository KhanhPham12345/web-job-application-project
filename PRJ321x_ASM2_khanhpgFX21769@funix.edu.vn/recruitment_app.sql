-- Create schema and select it
CREATE DATABASE IF NOT EXISTS spring_workcv;
USE spring_workcv;
-- Temporarily disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- Drop existing tables if they exist to start fresh
DROP TABLE IF EXISTS `applypost`, `cv`, `user_role`, `role`, `work_experience`, `recruitment`, `company`, `category`, `user`;

-- Create company table
CREATE TABLE company (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255) DEFAULT NULL,
    description TEXT DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    logo VARCHAR(255) DEFAULT NULL,
    name_company VARCHAR(255) DEFAULT NULL,
    phone_number VARCHAR(255) DEFAULT NULL,
    status INT(11) DEFAULT NULL
);

-- Insert data into company table
INSERT INTO company (address, description, email, logo, name_company, phone_number, status)
VALUES
('123 Elm Street, Cityville', 'A tech company focused on developing innovative software solutions.', 'info@techinnovators.com', 'tech_logo.png', 'Tech Innovators', '09115551234', 1),
('456 Oak Avenue, Townsville', 'A leading construction firm specializing in commercial and residential projects.', 'contact@buildright.com', 'buildright_logo.png', 'BuildRight Construction', '09125555678', 1),
('789 Pine Road, Villagetown', 'Non-profit organization dedicated to improving community welfare.', 'support@helpinghands.org', 'helpinghands_logo.png', 'Helping Hands', '09135559012', 1),
('101 Maple Drive, Metropolis', 'Global logistics and supply chain management company.', 'logistics@globalsolutions.com', 'global_logo.png', 'Global Solutions', '0915553456', 1),
('202 Birch Lane, Rivercity', 'A boutique marketing agency specializing in digital strategies and branding.', 'hello@creativeminds.com', 'creative_logo.png', 'Creative Minds', '0905557890', 1);

-- Create category table
CREATE TABLE category (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255) NOT NULL
);

INSERT INTO category (category) 
VALUES 
    ('JAVA'), 
    ('NODE.JS'), 
    ('ASP.NET'), 
    ('PHP'), 
    ('others');

-- Create user table
CREATE TABLE `user` (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255) DEFAULT NULL,
    description VARCHAR(255) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    full_name VARCHAR(255) DEFAULT NULL,
    image VARCHAR(255) DEFAULT NULL,
    password VARCHAR(128) DEFAULT NULL,
    phone_number VARCHAR(255) DEFAULT NULL,
    status INT(11) DEFAULT 1,
    username VARCHAR(255) DEFAULT NULL
);

-- Insert data into user table
INSERT INTO `user` (username, `password`, email)
VALUES 
('johndoe123', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'john@luv2code.com'),
('marypublic123', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'mary@luv2code.com'),
('susanadams123', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'susan@luv2code.com');

-- Create recruitment table
CREATE TABLE recruitment (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255) DEFAULT NULL,
    created_at DATE DEFAULT NULL,
    description VARCHAR(255) DEFAULT NULL,
    experience VARCHAR(255) DEFAULT NULL,
    quantity INT(11) DEFAULT NULL,
    `rank` VARCHAR(255) DEFAULT NULL,
    salary Int(11) DEFAULT NULL,
    status INT(11) DEFAULT NULL,
    title VARCHAR(255) DEFAULT NULL,
    type VARCHAR(255) DEFAULT NULL,
    view INT(11) DEFAULT 0,
    category_id INT(11) DEFAULT NULL,
    company_id INT(11) DEFAULT NULL,
    user_id INT(11) DEFAULT NULL,
    deadline VARCHAR(255) DEFAULT NULL,
    CONSTRAINT FK_recruitment_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_recruitment_company FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_recruitment_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Create role table
CREATE TABLE role (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL
);

-- Create user_role table
CREATE TABLE user_role (
    user_id INT(11),
    role_id INT(11),
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT FK_user_role_user FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_user_role_role FOREIGN KEY (role_id) REFERENCES `role`(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Insert data into user_role table
INSERT INTO `user_role` (user_id, role_id)
VALUES 
(1, 1),
(1, 2),
(2, 1),
(2, 3),
(3, 1),
(3, 4);

-- Create cv table
CREATE TABLE cv (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    file_name LONGBLOB DEFAULT NULL,
    user_id INT(11) DEFAULT NULL,
    file_name_text VARCHAR(255),
    CONSTRAINT FK_cv_user FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create work_experience table
CREATE TABLE work_experience (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT(11),
    company_name VARCHAR(255),
    position VARCHAR(255),
    description TEXT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- Create applypost table
CREATE TABLE applypost (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at DATE DEFAULT NULL,
    recruitment_id INT(11) DEFAULT NULL,
    user_id INT(11) DEFAULT NULL,
    cv_id INT(11) DEFAULT NULL,
    name_cv VARCHAR(255) DEFAULT NULL,
    status INT(11) DEFAULT NULL,
    text VARCHAR(255) DEFAULT NULL,
    CONSTRAINT FK_apply_recruitment FOREIGN KEY (recruitment_id) REFERENCES recruitment(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_apply_user FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- DELETE a
-- FROM applypost a
-- INNER JOIN applypost b ON a.id = b.id
-- WHERE b.cv_id = 6 OR b.cv_id = 7;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;
