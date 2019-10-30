INSERT INTO customers(id, date_of_birth, email, first_name, last_name, phone_number) VALUES(1, '1980-01-01', 'bank-customer-1@bank.com', 'Bank', 'Customer 1', '+37211223344');
INSERT INTO customers(id, date_of_birth, email, first_name, last_name, phone_number) VALUES(999, '1980-01-01', 'without-tw-999@bank.com', 'Bank', 'Customer Without TW', '+37211223344');
INSERT INTO customers(id, date_of_birth, email, first_name, last_name, phone_number) VALUES(888, '1980-01-01', 'with-tw-888@bank.com', 'Bank', 'Customer With TW', '+37211223344');

INSERT INTO tw_user(tw_user_id, active, customer_id, email, registration_code) VALUES(1, true, 1, 'bank-customer-1@bank.com', '6d4f2f7c-3474-4b36-bbdc-225993cc3896');

INSERT INTO tw_profile(tw_profile_id, customer_id, type) VALUES(1, 1, 'personal');

INSERT INTO tw_user_tokens(customer_id, access_token, expiry_time, refresh_token, tw_user_id) VALUES(1, 'cda71663-3481-4a67-aea0-8c57d56cef24', '2019-10-30 01:47:39.490205', '5e065dbd-72fe-414b-ad56-9eaa2b45bf31', 1);