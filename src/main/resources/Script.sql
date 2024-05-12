

CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(100), 
    address TEXT
);


CREATE TABLE customer_measurements (
    measurement_id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL, 
    neck NUMERIC,
    shoulder NUMERIC,
    chest NUMERIC,
    waist NUMERIC,
    hip NUMERIC,
    sleeve_length NUMERIC,
    inseam NUMERIC,
    other_measurements TEXT,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) 
);


select * from customers c 

select * from customer_measurements cm 

SELECT customers.*, customer_measurements.*
FROM customers
JOIN customer_measurements ON customers.customer_id = customer_measurements.customer_id
-- Optionally, add a WHERE clause to filter for a specific customer

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE, -- Optional, but recommended
    password VARCHAR(255) NOT NULL, -- Make sure to store passwords securely (hashing)
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

select * from friends f 

CREATE TABLE friends (
    friend_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL, -- Assuming you have a 'users' table to identify yourself
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50),
    phone_number VARCHAR(20),
    email VARCHAR(100),
    address text,
    notes TEXT, 
    date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) -- Adjust 'users' if your user table is named differently
);


ALTER TABLE friends ALTER COLUMN user_id DROP NOT NULL;





SELECT pg_get_serial_sequence('friends', 'friend_id');

public.friends_friend_id_seq

ALTER SEQUENCE public.friends_friend_id_seq AS BIGINT;  

ALTER TABLE friends ALTER COLUMN friend_id TYPE BIGINT;

ALTER SEQUENCE friends_friend_id_seq OWNED BY friends.friend_id;




UPDATE public.friends
SET user_id=NULL, first_name='Subratsen', last_name='Lakra', phone_number='7008087897', email='subratsenl@gmail.com', address='E-196, fertilizer township, Rourkela', notes='Rodeo Wala awara ashiq'
WHERE friend_id=9;





CREATE TABLE customer_details (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    address TEXT
);


select * from public.customer_details_demo cdd 


