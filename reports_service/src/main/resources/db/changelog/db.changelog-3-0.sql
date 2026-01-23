--liquibase formatted sql

--changeset vorobeva:3

CREATE TABLE report_order (
                              id BIGSERIAL PRIMARY KEY,
                              report_id BIGINT NOT NULL,
                              range_Start DATE,
                              range_End DATE,
                              user_email VARCHAR(255),
                              file_path VARCHAR(255),
                              status VARCHAR(50),
                              date TIMESTAMP,
                              FOREIGN KEY (report_id) REFERENCES report(report_id)
);

