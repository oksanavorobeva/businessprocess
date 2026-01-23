--liquibase formatted sql

--changeset vorobeva:4

CREATE TABLE sales_report_data (
                                   product_id BIGSERIAL PRIMARY KEY,
                                   product_name VARCHAR(255) NOT NULL,
                                   sale_date DATE NOT NULL,
                                   quantity_sold INT NOT NULL,
                                   price_per_unit DECIMAL(19, 2) NOT NULL  -- Или DECIMAL(10, 2) - в зависимости от требуемой точности
);

-- Заполнение данными (примеры)
INSERT INTO sales_report_data (product_id, product_name, sale_date, quantity_sold, price_per_unit) VALUES
    (1, 'Laptop', '2023-11-01', 10, 1200.00);

INSERT INTO sales_report_data (product_id, product_name, sale_date, quantity_sold, price_per_unit) VALUES
    (2, 'Mouse', '2023-11-01', 50, 25.50);

INSERT INTO sales_report_data (product_id, product_name, sale_date, quantity_sold, price_per_unit) VALUES
    (3, 'Keyboard', '2023-11-02', 20, 75.00);


