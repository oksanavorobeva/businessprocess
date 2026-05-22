--liquibase formatted sql

--changeset vorobeva:1
-- Сначала создаем независимую таблицу Topic
CREATE TABLE topic
(
    id         BIGSERIAL PRIMARY KEY,
    topic_name VARCHAR(255)
);

-- Затем создаем таблицу Report (ReportTemplate), так как она ссылается на Topic
CREATE TABLE report
(
    report_id   BIGSERIAL PRIMARY KEY,
    report_name VARCHAR(255),
    topic_id    BIGINT NOT NULL,

    -- Внешний ключ для связи ManyToOne
    CONSTRAINT fk_report_topic
        FOREIGN KEY (topic_id)
            REFERENCES topic (id)
            ON DELETE CASCADE -- Опционально: удалять отчеты при удалении топика
);