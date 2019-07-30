CREATE TABLE Credentials(
    id SERIAL PRIMARY KEY,
    access_token VARCHAR NOT NULL,
    refresh_token VARCHAR NOT NULL
);

CREATE TABLE Customer(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    credentials_id integer REFERENCES Credentials
);

INSERT INTO Customer(name) VALUES('John Travolta');