--liquibase formatted sql

--changeset vorobeva:2

INSERT INTO report (report_name, topic)
VALUES ('Sales report', 'sales_reports-events-topic'),
       ('Анализ пользовательской активности', 'customer_reports-events-topic'),
       ('Ежемесячный финансовый отчет', 'sales_reports-events-topic');
