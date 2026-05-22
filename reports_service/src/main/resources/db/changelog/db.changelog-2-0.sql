--liquibase formatted sql

--changeset vorobeva:1

INSERT INTO topic (id, topic_name)
VALUES (1, 'sales_reports-events-topic'),
       (2, 'customer_reports-events-topic')
ON CONFLICT (id) DO NOTHING;

-- Теперь, когда id 1 и 2 точно существуют, вставляем отчеты
INSERT INTO report (report_name, topic_id)
VALUES ('Sales report', 1),
       ('Анализ пользовательской активности', 2),
       ('Ежемесячный финансовый отчет', 1);
