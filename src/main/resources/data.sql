-- Table Schemas
DROP TABLE IF EXISTS wallets;
DROP TABLE IF EXISTS transactions;

CREATE TABLE wallets
(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    customer_id int UNIQUE NOT NULL,
    wallet_balance DECIMAL(22, 4) NOT NULL
);

CREATE TABLE transactions
(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    transaction_id int UNIQUE NOT NULL,
    customer_id int NOT NULL,
    transaction_type CHAR NOT NULL,
    withdrawal_amount DECIMAL(22, 4),
    deposit_amount DECIMAL(22, 4),
    transaction_time TIMESTAMP NOT NULL
);

INSERT INTO wallets(customer_id, wallet_balance)
VALUES (1000, 305.17),
       (1001, 1568.598),
       (1002, 202.5),
       (1003, 42622.32),
       (1004, 9007.143),
       (1005, 1458.4333),
       (1006, 42.4),
       (1007, 64566.8643),
       (1008, 4298.409),
       (1009, 96.3812),
       (1010, 6.387),
       (1011, 698.96),
       (1012, 128375.771);


INSERT INTO transactions(transaction_id, customer_id, transaction_type, withdrawal_amount, deposit_amount, transaction_time)
VALUES (200, 1008, 'W', 350.0, null, '2022-08-25 14:02:25'),
       (201, 1002, 'D', null, 3500.0, '2022-08-25 15:05:34'),
       (202, 1007, 'W', 4500.0, null, '2022-08-25 15:34:33'),
       (203, 1007, 'W', 1750.0, null, '2022-08-25 17:13:27'),
       (204, 1011, 'D', null, 1000.0, '2022-08-25 18:07:34'),
       (205, 1004, 'D', null, 5000.0, '2022-08-25 18:54:30')
       (206, 1002, 'W', 850.0, null, '2022-08-25 19:22:27'),
       (207, 1010, 'D', null, 625.0, '2022-08-25 19:47:34'),
       (208, 1005, 'D', null, 2500.0, '2022-08-25 20:51:30');
