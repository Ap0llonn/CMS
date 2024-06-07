-- Drop the 'cms' schema if it exists, including all its objects
DROP SCHEMA IF EXISTS cms CASCADE;

-- Create a new schema named 'cms'
CREATE SCHEMA cms;

-- Set the search path to the 'cms' schema
SET search_path TO cms;

-- Create 'type' table to store different types of employees
CREATE TABLE type (
    id SERIAL PRIMARY KEY,    -- Primary key with auto-increment
    name VARCHAR(50) NOT NULL -- Name of the type, not null
);

-- Create 'department' table to store different departments
CREATE TABLE department (
    id SERIAL PRIMARY KEY,    -- Primary key with auto-increment
    name VARCHAR(100) NOT NULL -- Name of the department, not null
);

-- Create 'program' table to store different programs offered by departments
CREATE TABLE program (
    id SERIAL PRIMARY KEY,        -- Primary key with auto-increment
    name VARCHAR(100) NOT NULL,   -- Name of the program, not null
    department_id INT,            -- Foreign key referencing 'department'
    FOREIGN KEY (department_id) REFERENCES department(id)
);

-- Create 'user' table to store user information
CREATE TABLE person (
    id SERIAL PRIMARY KEY,         -- Primary key with auto-increment
    first_name VARCHAR(100) NOT NULL,    -- First name, not null
    last_name VARCHAR(100) NOT NULL, -- Last name, not null
    email VARCHAR(100) NOT NULL,   -- Email, not null
    phone VARCHAR(15),             -- Phone number
    password VARCHAR(100) NOT NULL,-- Password, not null
    date_of_birth DATE             -- Date of birth
);

-- Create 'employee' table to store employee details linked to users
CREATE TABLE employee (
    employee_id SERIAL PRIMARY KEY, -- Primary key with auto-increment
    person_id INT NOT NULL UNIQUE, -- Foreign key referencing 'person', not null, unique
    seniority VARCHAR(50), -- Seniority level
    type_id INT, -- Foreign key referencing 'type'
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE, -- Delete employee if person is deleted
    FOREIGN KEY (type_id) REFERENCES type(id)
);

-- Create 'teacher' table to store teacher-specific information
CREATE TABLE teacher (
    employee_id INT NOT NULL UNIQUE PRIMARY KEY, -- Foreign key referencing 'employee', not null, unique
    department_id INT, -- Foreign key referencing 'department'
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE, -- Delete teacher if employee is deleted
    FOREIGN KEY (department_id) REFERENCES department(id)
);

-- Create 'course' table to store course information
CREATE TABLE course (
    id SERIAL PRIMARY KEY,           -- Primary key with auto-increment
    name VARCHAR(100) NOT NULL,      -- Course name, not null
    sigle VARCHAR(10),               -- Course code
    department_id INT,               -- Foreign key referencing 'department'
    teacher_id INT,                  -- Foreign key referencing 'teacher'
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);

-- Create 'academic_year' table to store academic year details
CREATE TABLE academic_year (
    year_id SERIAL PRIMARY KEY,      -- Primary key for academic year ID
    start_date DATE NOT NULL,        -- Start date of the academic year, not null
    end_date DATE NOT NULL           -- End date of the academic year, not null
);

-- Create 'session' table to store session details within academic years
CREATE TABLE session (
    session_id SERIAL PRIMARY KEY,    -- Primary key with auto-increment
    year_id INT NOT NULL,               -- Foreign key referencing 'academic_year', not null
    name VARCHAR(100) NOT NULL,       -- Session name, not null
    start_date DATE NOT NULL,         -- Start date of the session, not null
    end_date DATE NOT NULL,           -- End date of the session, not null
    FOREIGN KEY (year_id) REFERENCES academic_year(year_id)
);

-- Create 'student' table to store student details
CREATE TABLE student (
    person_id INT NOT NULL UNIQUE, -- Foreign key referencing 'person', not null, unique
    program_id INT NOT NULL, -- Foreign key referencing 'program', not null
    session_id INT NOT NULL, -- Foreign key referencing 'session', not null
    field VARCHAR(100), -- Field of study
    PRIMARY KEY (person_id),
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE, -- Delete student if person is deleted
    FOREIGN KEY (program_id) REFERENCES program(id),
    FOREIGN KEY (session_id) REFERENCES session(session_id)
);

-- Create 'enrollment' table to store enrollment details for students
CREATE TABLE enrollment (
    enrollment_id SERIAL PRIMARY KEY,   -- Primary key with auto-increment
    person_id INT NOT NULL,             -- Foreign key referencing 'student', not null
    session_id INT NOT NULL,            -- Foreign key referencing 'session', not null
    course_id INT NOT NULL,             -- Foreign key referencing 'course', not null
    end_date DATE,                      -- End date of the enrollment
    grade VARCHAR(2),                   -- Grade received
    FOREIGN KEY (person_id) REFERENCES student(person_id) ON DELETE CASCADE, -- Delete enrollment if student is deleted
    FOREIGN KEY (session_id) REFERENCES session(session_id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

-- Create 'evaluation' table to store evaluation details for courses
CREATE TABLE evaluation (
    id SERIAL PRIMARY KEY,           -- Primary key with auto-increment
    name VARCHAR(100) NOT NULL,      -- Evaluation name, not null
    course_id INT NOT NULL,          -- Foreign key referencing 'course', not null
    ponderation INT,                 -- Weight of the evaluation
    denominator INT,                 -- Denominator for grading
    FOREIGN KEY (course_id) REFERENCES course(id)
);

-- Create 'student_evaluation' table to store student evaluation results
CREATE TABLE student_evaluation (
    person_id INT NOT NULL,            -- Foreign key referencing 'student', not null
    evaluation_id INT NOT NULL,        -- Foreign key referencing 'evaluation', not null
    PRIMARY KEY (person_id, evaluation_id), -- Composite primary key
    FOREIGN KEY (person_id) REFERENCES student(person_id) ON DELETE CASCADE, -- Delete record if student is deleted
    FOREIGN KEY (evaluation_id) REFERENCES evaluation(id)
);

-- Create 'student_course' table to link students with their courses
CREATE TABLE student_course (
    person_id INT NOT NULL,            -- Foreign key referencing 'student', not null
    course_id INT NOT NULL,            -- Foreign key referencing 'course', not null
    PRIMARY KEY (person_id, course_id),-- Composite primary key
    FOREIGN KEY (person_id) REFERENCES student(person_id) ON DELETE CASCADE, -- Delete record if student is deleted
    FOREIGN KEY (course_id) REFERENCES course(id)
);

-- Create 'schedule' table to store class schedules for students
CREATE TABLE schedule (
    id SERIAL PRIMARY KEY,           -- Primary key with auto-increment
    person_id INT NOT NULL,          -- Foreign key referencing 'student', not null
    course_id INT NOT NULL,          -- Foreign key referencing 'course', not null
    hour_start VARCHAR(5),           -- Class start time
    hour_end VARCHAR(5),             -- Class end time
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE, -- Delete record if person is deleted
    FOREIGN KEY (course_id) REFERENCES course(id)
);

-- Create 'mail' table to store emails between users
CREATE TABLE mail (
    id SERIAL PRIMARY KEY,           -- Primary key with auto-increment
    receiver_id INT NOT NULL,        -- Foreign key referencing 'user', not null
    sender_id INT NOT NULL,          -- Foreign key referencing 'user', not null
    subject VARCHAR(100),            -- Email subject
    content TEXT,                    -- Email content
    date DATE,                       -- Date of the email
    read BOOLEAN,                    -- Read status
    FOREIGN KEY (receiver_id) REFERENCES person(id) ON DELETE CASCADE, -- Delete mail if receiver is deleted
    FOREIGN KEY (sender_id) REFERENCES person(id) ON DELETE CASCADE    -- Delete mail if sender is deleted
);

-- Create 'address' table to store addresses linked to users
CREATE TABLE address (
    id SERIAL PRIMARY KEY,           -- Primary key with auto-increment
    person_id INT NOT NULL,            -- Foreign key referencing 'user', not null
    address VARCHAR(200),            -- Street address
    city VARCHAR(100),               -- City
    province VARCHAR(50),            -- Province
    postal_code VARCHAR(10),         -- Postal code
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE -- Delete address if user is deleted
);

-- Function to delete emails that are marked as read and older than 30 days
CREATE OR REPLACE FUNCTION delete_old_read_emails()
RETURNS TRIGGER AS $$
BEGIN 
    IF OLD.read = true THEN
        DELETE FROM mail WHERE id = OLD.id AND date < current_date - interval '30 days';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Trigger to automatically delete old read emails after the 'read' field is updated
CREATE TRIGGER trigger_delete_old_read_emails
AFTER UPDATE OF read ON mail
FOR EACH ROW
EXECUTE FUNCTION delete_old_read_emails();

-- Insert initial data into the 'type' table
INSERT INTO type (name) VALUES ('Teacher');
INSERT INTO type (name) VALUES ('Intern');
INSERT INTO type (name) VALUES ('Admin');
INSERT INTO type (name) VALUES ('Janitor');
INSERT INTO type (name) VALUES ('HR');
INSERT INTO type (name) VALUES ('Manager');

-- Insert initial data into the 'department' table
INSERT INTO department (name) VALUES ('Technology');
INSERT INTO department (name) VALUES ('Health');
INSERT INTO department (name) VALUES ('Mathematics');
INSERT INTO department (name) VALUES ('French');
INSERT INTO department (name) VALUES ('English');
INSERT INTO department (name) VALUES ('Physical Education');
INSERT INTO department (name) VALUES ('Chemistry');
INSERT INTO department (name) VALUES ('Engineering');
INSERT INTO department (name) VALUES ('Pre-university');

-- Insert initial data into the 'program' table
INSERT INTO program (name, department_id) VALUES ('Techniques de pharmacie', (SELECT id FROM department WHERE name = 'Health'));
INSERT INTO program (name, department_id) VALUES ('Développement d’applications Web et Mobiles', (SELECT id FROM department WHERE name = 'Technology'));
INSERT INTO program (name, department_id) VALUES ('Gestion, communications administratives et médias sociaux', (SELECT id FROM department WHERE name = 'Technology'));
INSERT INTO program (name, department_id) VALUES ('Environnement, hygiène et sécurité au travail', (SELECT id FROM department WHERE name = 'Health'));
INSERT INTO program (name) VALUES ('Gestion d’un établissement de restauration');
INSERT INTO program (name, department_id) VALUES ('Soins infirmiers', (SELECT id FROM department WHERE name = 'Health'));
INSERT INTO program (name) VALUES ('Techniques d’éducation spécialisée');
INSERT INTO program (name, department_id) VALUES ('Techniques de Comptabilité et de gestion', (SELECT id FROM department WHERE name = 'Technology'));
INSERT INTO program (name, department_id) VALUES ('Techniques de génie mécanique', (SELECT id FROM department WHERE name = 'Engineering'));
INSERT INTO program (name) VALUES ('Techniques juridiques');
INSERT INTO program (name, department_id) VALUES ('Technologie du génie électrique : automatisation et contrôle', (SELECT id FROM department WHERE name = 'Engineering'));
INSERT INTO program (name, department_id) VALUES ('Sciences de la nature', (SELECT id FROM department WHERE name = 'Pre-university'));
INSERT INTO program (name, department_id) VALUES ('Sciences humaines', (SELECT id FROM department WHERE name = 'Pre-university'));
INSERT INTO program (name, department_id) VALUES ('Arts, lettres et communication : profil arts, médias et société', (SELECT id FROM department WHERE name = 'Pre-university'));
