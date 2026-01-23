--liquibase formatted sql

--changeset vorobeva:3

-- Заполнение данными (примеры)
INSERT INTO report_configurations (report_name, strategy_bean_name, description, is_active)
VALUES ('Sales report', 'simpleSalesReportStrategy', 'Simple sales report configuration', TRUE);

