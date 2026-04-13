CREATE TABLE tb_attendances
(
    id         UUID NOT NULL,
    lesson_id  UUID,
    student_id UUID,
    status     VARCHAR(255),
    CONSTRAINT pk_tb_attendances PRIMARY KEY (id)
);

CREATE TABLE tb_classrooms
(
    id      UUID NOT NULL,
    grade   INTEGER,
    letter  CHAR,
    shift   VARCHAR(255),
    removed BOOLEAN,
    CONSTRAINT pk_tb_classrooms PRIMARY KEY (id)
);

CREATE TABLE tb_lessons
(
    id           UUID NOT NULL,
    classroom_id UUID,
    teacher_id   UUID,
    date         date,
    CONSTRAINT pk_tb_lessons PRIMARY KEY (id)
);

CREATE TABLE tb_students
(
    id           UUID NOT NULL,
    name         VARCHAR(255),
    birth_date   date,
    classroom_id UUID,
    CONSTRAINT pk_tb_students PRIMARY KEY (id)
);

CREATE TABLE tb_subjects
(
    id      UUID NOT NULL,
    name    VARCHAR(255),
    removed BOOLEAN,
    CONSTRAINT pk_tb_subjects PRIMARY KEY (id)
);

CREATE TABLE tb_teachers
(
    id         UUID NOT NULL,
    name       VARCHAR(255),
    subject_id UUID,
    removed    BOOLEAN,
    CONSTRAINT pk_tb_teachers PRIMARY KEY (id)
);

ALTER TABLE tb_attendances
    ADD CONSTRAINT FK_TB_ATTENDANCES_ON_LESSON FOREIGN KEY (lesson_id) REFERENCES tb_lessons (id);

ALTER TABLE tb_attendances
    ADD CONSTRAINT FK_TB_ATTENDANCES_ON_STUDENT FOREIGN KEY (student_id) REFERENCES tb_students (id);

ALTER TABLE tb_lessons
    ADD CONSTRAINT FK_TB_LESSONS_ON_CLASSROOM FOREIGN KEY (classroom_id) REFERENCES tb_classrooms (id);

ALTER TABLE tb_lessons
    ADD CONSTRAINT FK_TB_LESSONS_ON_TEACHER FOREIGN KEY (teacher_id) REFERENCES tb_teachers (id);

ALTER TABLE tb_students
    ADD CONSTRAINT FK_TB_STUDENTS_ON_CLASSROOM FOREIGN KEY (classroom_id) REFERENCES tb_classrooms (id);

ALTER TABLE tb_teachers
    ADD CONSTRAINT FK_TB_TEACHERS_ON_SUBJECT FOREIGN KEY (subject_id) REFERENCES tb_subjects (id);