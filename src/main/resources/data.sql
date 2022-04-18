insert into guestbook.user (user_id,active,create_time,email_id,password,user_name,role_id)
values
(1000, 1, curdate(), 'abcd@gmail.com', '$2a$10$fpkC2uWzZH9s5hu9u85ImOl7ONr8s2WcL2bCV87F5s9ywRGwxTDHm', 'user',100);

insert into guestbook.user (user_id,active,create_time,email_id,password,user_name,role_id)
values
(1001, 1, curdate(), 'xyz@gmail.com', '$2a$10$5k2DhSFrK4Lpzmom2yW3ku.HWy88yxf/SIvv7Jn5JprU6N7yKG5am', 'admin',200);

insert into guestbook.user (user_id,active,create_time,email_id,password,user_name,role_id)
values
(1002, 1, curdate(), 'gowrish@gmail.com', '$2a$10$4.GUCX1wIO8jY6r7F4clfOwYTuR6dB64ICJHIL3f1geEYc70cEECW', 'Gowrish',100);

insert into guestbook.user (user_id,active,create_time,email_id,password,user_name,role_id)
values
(1003, 1, curdate(), 'sam@gmail.com', '$2a$10$n0N9QS8zMN0vLbEW8zp4qefV9IPgoahBG6VmxiERYXc2Ueod7GISK', 'Sam',200);

insert into guestbook.user (user_id,active,create_time,email_id,password,user_name,role_id)
values
(1004, 1, curdate(), 'rob@gmail.com', '$2a$10$fsERqJBHtibfauRA3S/ueeEPwqdn5U9dK.3pk3vZ5p.6toTK7LoKa', 'Rob',300);

insert into guestbook.role (role_id,role)values (100, 'GUEST');
insert into guestbook.role (role_id,role)values (200, 'ADMIN');
insert into guestbook.role (role_id,role)values (300, 'MANAGER');