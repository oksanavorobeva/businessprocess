--liquibase formatted sql

--changeset vorobeva:2

CREATE TABLE IF NOT EXISTS report_configurations
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    report_name
    VARCHAR
(
    255
) NOT NULL UNIQUE,
    strategy_bean_name VARCHAR
(
    255
) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE
    );
