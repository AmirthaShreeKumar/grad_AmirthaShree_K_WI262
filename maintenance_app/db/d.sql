CREATE TABLE admin_login(
 id SERIAL PRIMARY KEY,
 username VARCHAR(50),
 password VARCHAR(50)
);
ALTER TABLE sites ADD COLUMN status VARCHAR(20) DEFAULT 'OCCUPIED';
ALTER TABLE sites ADD COLUMN maintenance_due DOUBLE PRECISION DEFAULT 0;

INSERT INTO admin_login(username,password)
VALUES('admin','1234');

CREATE TABLE owners(
 owner_id SERIAL PRIMARY KEY,
 name VARCHAR(50),
 username VARCHAR(50),
 password VARCHAR(50)
);

INSERT INTO owners(name,username,password)
VALUES('Anandh','anandh12345','anandh1234');

CREATE TABLE sites(
 site_id SERIAL PRIMARY KEY,
 owner_id INT,
 site_type VARCHAR(20),
 size VARCHAR(10),
 sqft INT,
 FOREIGN KEY(owner_id) REFERENCES owners(owner_id)
);

CREATE TABLE site_requests(
 req_id SERIAL PRIMARY KEY,
 owner_id INT,
 site_type VARCHAR(20),
 size VARCHAR(10),
 sqft INT,
 status VARCHAR(20) DEFAULT 'PENDING'
);
select *from sites;



