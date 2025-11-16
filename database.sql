CREATE DATABASE IF NOT EXISTS studentdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'student_user'@'%' IDENTIFIED BY 'StrongP@ssw0rd!';
GRANT ALL PRIVILEGES ON studentdb.* TO 'student_user'@'%';
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS students (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  course VARCHAR(255) NOT NULL,
  age INT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_students_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Optional indexes for search
CREATE INDEX idx_students_name ON students (name);
CREATE INDEX idx_students_course ON students (course);