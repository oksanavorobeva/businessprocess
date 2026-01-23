--liquibase formatted sql

--changeset vorobeva:1
CREATE TABLE IF NOT EXISTS processed_events_sales_metrics_worker
(
    id              BIGSERIAL PRIMARY KEY,
    message_id      VARCHAR(255) NOT NULL UNIQUE, -- или TEXT, если ожидается очень длинный текст
    report_event_id BIGINT       NOT NULL,
    report_name     VARCHAR(255) NOT NULL      -- или TEXT, если ожидается очень длинный текст
);

