-- Customer Details Table
CREATE TABLE customer_details_demo (
    c_id SERIAL PRIMARY KEY,
    c_name VARCHAR(100) NOT NULL,
    c_email VARCHAR(100),
    c_phone VARCHAR(20),
    c_address TEXT
);

-- Measurements Table
CREATE TABLE customer_measurements_demo (
    m_id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customer_details_demo(c_id),
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


CREATE TABLE public.customer_measurements_demo (
    m_id BIGSERIAL PRIMARY KEY,  -- Auto-incrementing ID for measurements
    c_id BIGINT NOT NULL REFERENCES customer_details_demo(c_id), -- Foreign key to customer
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

CREATE TABLE customer_order (
    o_id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customer_details_demo(c_id),
    status VARCHAR(50),
    time_slot TIMESTAMP,
    note TEXT
);

CREATE TABLE availability_demo (
    a_id SERIAL PRIMARY KEY,
    date DATE,
    time_slots TEXT[]
);

CREATE TABLE time_slot_demo (
    t_id SERIAL PRIMARY KEY,
    availability_id INT REFERENCES availability_demo(a_id),
    time_slot_value VARCHAR(50)
);


select * from public.customer_measurements_demo cmd 


select * from public.availability_demo ad

select * from public.time_slot_demo tsd 


select * from public.customer_details_demo cdd

select * from public.customer_measurements_demo cmd 

 

INSERT INTO customer_measurements_demo (c_id, bust, waist, hips, inseam, height, shoulder_width, arm_length, thigh, calf, neck)
VALUES (1, 90.5, 75.0, 98.2, 78.3, 165.1, 40.6, 62.8, 58.4, 38.1, 35.6);




INSERT INTO customer_details_demo (c_name, c_email, c_phone, c_address)
VALUES ('Alice Johnson', 'alice.johnson@example.com', '123-456-7890', '123 Main St, Anytown USA');




















