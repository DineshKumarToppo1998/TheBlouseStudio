-- Customer Details Table(Production)
CREATE SEQUENCE customer_id_seq INCREMENT BY 1 START WITH 10000; -- Starting from 10000 for 5 digits
CREATE TABLE customer_details (
    id INT DEFAULT NEXTVAL('customer_id_seq') PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    address TEXT
);

-- Measurements Table
CREATE SEQUENCE measurement_id_seq INCREMENT BY 1 START WITH 10000000; -- Starting from 10000000 for 8 digits
CREATE TABLE customer_measurements (
    id INT DEFAULT NEXTVAL('measurement_id_seq') PRIMARY KEY,
    customer_id INT REFERENCES customer_details(id),
    bust FLOAT,
    waist FLOAT,
    hips FLOAT,
    inseam FLOAT,
    height FLOAT,
    shoulder_width FLOAT,
    arm_length FLOAT,
    thigh FLOAT,
    calf FLOAT,
    neck FLOAT
);