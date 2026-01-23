--liquibase formatted sql

--changeset vorobeva:1
CREATE TABLE report (
                        report_id BIGSERIAL PRIMARY KEY,
                        report_name VARCHAR(255),
                        topic VARCHAR(255)
);