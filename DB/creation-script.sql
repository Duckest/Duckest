CREATE TABLE quiz_level
(
    "level" TEXT NOT NULL CONSTRAINT quiz_level_pkey PRIMARY KEY
);

CREATE TABLE quiz_type
(
    "type" TEXT NOT NULL CONSTRAINT quiz_type_pkey PRIMARY KEY
);

CREATE TABLE person
(
    "login" TEXT NOT NULL CONSTRAINT person_pkey PRIMARY KEY,
    first_name TEXT,
    middle_name TEXT,
    last_name TEXT
);

CREATE TABLE role
(
    role_name TEXT NOT NULL CONSTRAINT role_pkey PRIMARY KEY
);

CREATE TABLE quiz_description
(
    id UUID NOT NULL CONSTRAINT quiz_description_pkey PRIMARY KEY,
    "description" TEXT NOT NULL,
    quiz_level TEXT CONSTRAINT description_quiz_level_fk REFERENCES quiz_level,
    quiz_type TEXT CONSTRAINT description_quiz_type_fk REFERENCES quiz_type
);

CREATE TABLE pass_threshold
(
    quiz_level TEXT NOT NULL CONSTRAINT threshold_quiz_level_fk REFERENCES quiz_level,
    quiz_type TEXT NOT NULL CONSTRAINT threshold_quiz_type_fk REFERENCES quiz_type,
    threshold INTEGER NOT NULL,
    CONSTRAINT pass_threshold_pkey PRIMARY KEY (quiz_level, quiz_type)
);

CREATE TABLE quiz_pass_date
(
    person_login TEXT NOT NULL CONSTRAINT pass_date_person_login_fk REFERENCES person,
    quiz_level TEXT NOT NULL CONSTRAINT pass_date_quiz_level_fk REFERENCES quiz_level,
    quiz_type TEXT NOT NULL CONSTRAINT pass_date_quiz_type_fk REFERENCES quiz_type,
    finish_date date NOT NULL,
    CONSTRAINT quiz_pass_date_pkey PRIMARY KEY (person_login, quiz_level, quiz_type)
);

CREATE TABLE progress
(
    person_login TEXT NOT NULL CONSTRAINT progress_person_login_fk REFERENCES person,
    quiz_level TEXT NOT NULL CONSTRAINT progress_quiz_level_fk REFERENCES quiz_level,
    quiz_type TEXT NOT NULL CONSTRAINT progress_question_type_fk REFERENCES quiz_type,
    progress INTEGER NOT NULL,
    CONSTRAINT progress_pkey PRIMARY KEY (person_login, quiz_level, quiz_type)
);

CREATE TABLE person_role
(
    person_login TEXT NOT NULL CONSTRAINT role_person_login_fk REFERENCES person on update cascade on delete cascade,
    role_name TEXT NOT NULL CONSTRAINT role_name_fk REFERENCES role,
    CONSTRAINT person_role_pkey PRIMARY KEY (person_login, role_name)
);

CREATE TABLE quiz_question
(
    id UUID NOT NULL CONSTRAINT quiz_question_pkey PRIMARY KEY,
    question TEXT NOT NULL,
    quiz_level TEXT CONSTRAINT question_quiz_level_fk REFERENCES quiz_level,
    quiz_type TEXT CONSTRAINT question_quiz_type_fk REFERENCES quiz_type
);

CREATE TABLE answer
(
    id UUID NOT NULL,
    question_id UUID NOT NULL CONSTRAINT answer_question_id_fk REFERENCES quiz_question on update cascade on delete cascade,
    answer TEXT NOT NULL,
    CONSTRAINT answer_pkey PRIMARY KEY (id, question_id)
);

CREATE TABLE right_answer
(
    id UUID NOT NULL CONSTRAINT right_answer_pkey PRIMARY KEY,
    question_id UUID NOT NULL,
    answer_id UUID NOT NULL,
    CONSTRAINT answer_id_question_id_fk FOREIGN KEY (question_id, answer_id) REFERENCES answer (question_id, id) on update cascade on delete cascade
);