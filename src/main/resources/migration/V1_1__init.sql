CREATE TABLE TIME_ENTRIES (
                        id bigint GENERATED BY DEFAULT AS IDENTITY,
                        project_id bigint,
                        user_id bigint,
                        date date,
                        hours int);
