--liquibase formatted sql

--changeset vorobeva:1

CREATE TABLE outbox
(
    id       BIGSERIAL PRIMARY KEY,
    payload    jsonb,
    created TIMESTAMP,
    type       VARCHAR(255)
);



