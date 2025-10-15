insert into admin (email, username, name, password, phone)
values
('admin1@gmail.com', 'admin_1', 'Admin First', 'changeme', '80008'),
('admin2@gmail.com', 'admin_2', 'Admin Second', 'changeme', '80008'),
('admin3@gmail.com', 'admin_3', 'Admin Third', 'changeme', '80008');

insert into institution (name, secret_key, unique_name, admin)
values
('Glorious', 'secret_key1', 'glorious144', 1),
('Paradise', 'secret_key2', 'paradise144', 2),
('Bhupendra', 'secret_key3', 'bhupendra144', 3);