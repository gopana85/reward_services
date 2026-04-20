-- Insert sample customers
INSERT INTO customers (customer_id, customer_code, customer_name, email)
VALUES (1, 'CUST-1001', 'Ava Thompson', 'ava.thompson@email.com');

INSERT INTO customers (customer_id, customer_code, customer_name, email)
VALUES (2, 'CUST-1002', 'Brad Johnson', 'brad.johnson@email.com');

INSERT INTO customers (customer_id, customer_code, customer_name, email)
VALUES (3, 'CUST-1003', 'Charlie Brown', 'charlie.brown@email.com');

-- Insert sample transactions
INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount)
VALUES (10001, 1, '2026-01-05', 120.00);

INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount)
VALUES (10002, 1, '2026-01-17', 76.45);

INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount)
VALUES (10003, 1, '2026-02-12', 210.10);

INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount)
VALUES (10004, 1, '2026-02-26', 49.99);

INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount)
VALUES (20001, 2, '2026-01-09', 45.00);

INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount)
VALUES (20002, 2, '2026-01-27', 51.00);

INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount)
VALUES (20003, 2, '2026-02-03', 101.00);

INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount)
VALUES (30001, 3, '2026-01-11', 150.00);

INSERT INTO transactions (transaction_id, customer_id, transaction_date, amount)
VALUES (30002, 3, '2026-02-15', 75.00);

