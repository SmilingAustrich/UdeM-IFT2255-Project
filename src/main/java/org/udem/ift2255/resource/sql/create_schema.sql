CREATE TABLE work_requests (
                               id SERIAL PRIMARY KEY,
                               title VARCHAR(255),
                               type VARCHAR(100),
                               neighbourhood VARCHAR(100),
                               start_date DATE
);
