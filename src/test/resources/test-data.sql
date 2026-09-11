TRUNCATE TABLE users RESTART IDENTITY;

INSERT INTO users (id, first_name, last_name, email, password)
VALUES (1, 'Existing', 'TestUser', 'existing@test.com', 'password123');

INSERT INTO users (id, first_name, last_name, email, password)
VALUES (2, 'Existing2', 'TestUser2', 'existing2@test.com', 'password123');

ALTER TABLE users ALTER COLUMN id RESTART WITH 32;