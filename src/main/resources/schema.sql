DROP TABLE IF EXISTS loan_objects;
DROP TABLE IF EXISTS objects;
DROP TABLE IF EXISTS loan;
DROP TABLE IF EXISTS location;


CREATE TABLE location (
	location_id int NOT NULL AUTO_INCREMENT,
	place_name varchar(256) NOT NULL,
	contact_name varchar(60) NOT NULL,
	phone varchar(30),
	email varchar(128),
	street_address varchar(256),
	city varchar(128),
	state varchar(60),
	zip varchar(20),
	mailing_street varchar(256),
	mailing_city varchar(128),
	mailing_state varchar(60),
	mailing_zip varchar(20),
	PRIMARY KEY(location_id)	
);

CREATE TABLE loan (
	loan_id int NOT NULL AUTO_INCREMENT,
	location_id int NOT NULL,
	status varchar(50),
	start_date varchar(60),
	end_date varchar(60),
	purpose varchar(500),
	PRIMARY KEY(loan_id),
	FOREIGN KEY(location_id) REFERENCES location (location_id) ON DELETE CASCADE
);

CREATE TABLE objects (
	object_id int NOT NULL AUTO_INCREMENT,
	catalog_number varchar(30) NOT NULL UNIQUE,
	common_name varchar(128),
	material_type varchar(128),
	PRIMARY KEY(object_id)
);

CREATE TABLE loan_objects (
	loan_id int NOT NULL,
	object_id int NOT NULL,
	FOREIGN KEY(loan_id) REFERENCES loan (loan_id) ON DELETE CASCADE,
	FOREIGN KEY(object_id) REFERENCES objects (object_id) ON DELETE CASCADE
);