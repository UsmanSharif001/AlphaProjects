CREATE table skill(
                      skill_id INTEGER NOT NULL AUTO_INCREMENT,
                      skill_name VARCHAR(200) NOT NULL,
                      PRIMARY KEY(skill_id)
);

CREATE table role(
                     role_id INTEGER NOT NULL AUTO_INCREMENT,
                     role_name VARCHAR(50) NOT NULL,
                     PRIMARY KEY(role_id)
);

CREATE table emp(
                    emp_id INTEGER NOT NULL AUTO_INCREMENT,
                    emp_name VARCHAR(200) NOT NULL,
                    emp_email VARCHAR(200) NOT NULL,
                    emp_password VARCHAR(200) NOT NULL,
                    role_id INTEGER NOT NULL,
                    PRIMARY KEY(emp_id),
                    FOREIGN KEY(role_id) REFERENCES role(role_id)
);

CREATE table emp_skills(
                           skill_id INTEGER NOT NULL,
                           emp_id INTEGER NOT NULL,
                           PRIMARY KEY(skill_id,emp_id),
                           FOREIGN KEY(skill_id) REFERENCES skill(skill_id),
                           FOREIGN KEY(emp_id) REFERENCES empDTO(emp_id)
);

CREATE table project(
                        project_id INTEGER NOT NULL AUTO_INCREMENT,
                        project_manager_id INT NOT NULL,
                        project_name VARCHAR(300) NOT NULL,
                        project_description VARCHAR(500),
                        project_time_estimate INT,
                        project_dedicated_hours INT,
                        project_deadline DATE,
                        project_status VARCHAR(200),
                        PRIMARY KEY(project_id),
                        FOREIGN KEY(project_manager_id) REFERENCES empDTO(emp_id)
);

CREATE table subproject(
                           subproject_id INTEGER NOT NULL AUTO_INCREMENT,
                           project_id INTEGER NOT NULL,
                           subproject_name VARCHAR(200) NOT NULL,
                           subproject_description VARCHAR(500),
                           subproject_time_estimate INT,
                           subproject_dedicated_hours INT,
                           subproject_deadline DATE,
                           subproject_status VARCHAR(200),
                           PRIMARY KEY(subproject_id),
                           FOREIGN KEY(project_id) REFERENCES project(project_id) ON DELETE CASCADE
);

CREATE table task(
                     task_id INTEGER NOT NULL AUTO_INCREMENT,
                     subproject_id INTEGER NOT NULL,
                     task_name VARCHAR(200) NOT NULL,
                     task_description VARCHAR(500),
                     task_time_estimate INT,
                     task_deadline DATE,
                     task_status VARCHAR(200),
                     PRIMARY KEY(task_id),
                     FOREIGN KEY(subproject_id) REFERENCES subproject(subproject_id) ON DELETE CASCADE
);

CREATE table task_emp(
                         task_id INT NOT NULL,
                         emp_id INT NOT NULL,
                         PRIMARY KEY(task_id,emp_id),
                         FOREIGN KEY(task_id) REFERENCES task(task_id)ON DELETE CASCADE,
                         FOREIGN KEY(emp_id) REFERENCES empDTO(emp_id)
);

INSERT into skill(skill_name)
VALUES
    ('Scrum Master'),
    ('Project Manager'),
    ('Java developer'),
    ('Junior Python developer');

INSERT into role(role_name)
VALUES
    ('Admin'),
    ('User');

INSERT into empDTO(emp_name,emp_email,emp_password,role_id)
VALUES
    ('Thea','Thea@gmail.com','123',1),
    ('Marie','Marie@gmail.com','123',2),
    ('Usman','Usman@gmail.com','123',2),
    ('Nikolaj','Nikolaj@gmail.com','123',1);

INSERT into emp_skills(skill_id,emp_id)
VALUES
    (1,1),
    (1,2),
    (2,3),
    (4,2),
    (1,4),
    (2,4),
    (3,4);

INSERT into project(project_manager_id, project_name, project_description, project_time_estimate, project_dedicated_hours, project_deadline,project_status)
VALUES
    (1, 'Alpha Project', 'Super awesome project', 100, 0, '2024-12-12', 'In_progress');

INSERT into subproject(project_id, subproject_name, subproject_description, subproject_time_estimate, subproject_dedicated_hours, subproject_deadline,subproject_status)
VALUES
    (1,'Backend','New funky backend', 25, 0,'2024-12-12','In_progress');

INSERT into task(subproject_id, task_name, task_description, task_time_estimate, task_deadline,task_status)
VALUES
    (1,'Create x method','You can do it', 3,'2024-10-12','In_progress'),
    (1,'Create y method','You can do it', 3,'2024-06-06','Done');

INSERT into task_emp(task_id,emp_id)
VALUES
    (1,3),
    (2,4);



