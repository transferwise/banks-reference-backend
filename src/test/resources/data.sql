INSERT INTO customers(id, date_of_birth, email, first_name, last_name, phone_number) VALUES(777, '1980-01-01', 'bank-customer-1@bank.com', 'Bank', 'Customer 1', '+37211223344');
INSERT INTO customers(id, date_of_birth, email, first_name, last_name, phone_number) VALUES(999, '1980-01-01', 'without-tw-999@bank.com', 'Bank', 'Customer Without TW', '+37211223344');
INSERT INTO customers(id, date_of_birth, email, first_name, last_name, phone_number) VALUES(888, '1980-01-01', 'with-tw-888@bank.com', 'Bank', 'Customer With TW', '+37211223344');

INSERT INTO tw_user(tw_user_id, active, customer_id, email, registration_code) VALUES(1, true, 777, 'bank-customer-1@bank.com', '6d4f2f7c-3474-4b36-bbdc-225993cc3896');

INSERT INTO tw_profile(tw_profile_id, customer_id, type, updated_at) VALUES(1, 777, 'personal', CURRENT_TIMESTAMP);

INSERT INTO tw_user_tokens(customer_id, access_token, expiry_time, refresh_token, tw_user_id) VALUES(777, 'cda71663-3481-4a67-aea0-8c57d56cef24', '2019-10-30 01:47:39.490205', '5e065dbd-72fe-414b-ad56-9eaa2b45bf31', 1);

INSERT INTO currencies(code, most_popular, name) VALUES('GBP', TRUE, 'Pound Sterling');
INSERT INTO currencies(code, most_popular, name) VALUES('EUR', TRUE, 'Euro');
INSERT INTO currencies(code, most_popular, name) VALUES('USD', TRUE, 'United States Dollar');
INSERT INTO currencies_countries(currencies_code, countries) VALUES('GBP', 'UK');
INSERT INTO currencies_countries(currencies_code, countries) VALUES('EUR', 'Belgium');
INSERT INTO currencies_countries(currencies_code, countries) VALUES('EUR', 'Portugal');
INSERT INTO currencies_countries(currencies_code, countries) VALUES('USD', 'United States of America');
