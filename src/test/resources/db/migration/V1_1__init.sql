CREATE TABLE TIME_ENTRIES (
                        id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        project_id bigint,
                        user_id bigint,
                        date date,
                        hours int);
