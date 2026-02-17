CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
    role VARCHAR(20) CHECK (role IN ('ADMIN','OWNER')) NOT NULL
);
CREATE TABLE sites (
    site_no INT PRIMARY KEY,
    dimension VARCHAR(20),
    area INT,
    property_type VARCHAR(30),   -- VILLA/APARTMENT/HOUSE/OPEN SITE
    per_sqft INT,
    total_amount INT,
    balance INT,
    status VARCHAR(20) DEFAULT 'PENDING',
    owner_id INT,
    site_type VARCHAR(20),       -- OPEN / OCCUPIED
    FOREIGN KEY(owner_id) REFERENCES users(user_id)
);


CREATE TABLE payments (
    payment_id SERIAL PRIMARY KEY,
    site_no INT,
    paid_amount INT,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(site_no) REFERENCES sites(site_no)
);


INSERT INTO users(username,password,role)
VALUES('admin','admin123','ADMIN');


INSERT INTO sites
(site_no,dimension,area,property_type,per_sqft,total_amount,balance,status,owner_id,site_type)
SELECT 
    gs,
    '40x60',
    2400,
    'OPEN SITE',
    6,
    2400*6,
    2400*6,
    'PENDING',
    NULL,
    'OPEN'
FROM generate_series(1,10) gs;


INSERT INTO sites
SELECT 
    gs,
    '30x50',
    1500,
    'OPEN SITE',
    6,
    1500*6,
    1500*6,
    'PENDING',
    NULL,
    'OPEN'
FROM generate_series(11,20) gs;




INSERT INTO sites
SELECT 
    gs,
    '30x40',
    1200,
    'OPEN SITE',
    6,
    1200*6,
    1200*6,
    'PENDING',
    NULL,
    'OPEN'
FROM generate_series(21,35) gs;



CREATE OR REPLACE FUNCTION update_status()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.balance = 0 THEN
        NEW.status = 'PAID';
    ELSE
        NEW.status = 'PENDING';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_update_status
BEFORE UPDATE ON sites
FOR EACH ROW
EXECUTE FUNCTION update_status();


INSERT INTO users(username,password,role)
VALUES('owner1','123','OWNER');

select *from users;
select *from sites;




