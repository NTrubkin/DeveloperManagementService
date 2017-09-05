-- Роли в проекте
INSERT INTO role (code, name) VALUES ('ROLE_ADMIN', 'Administrator');
INSERT INTO role (code, name) VALUES ('ROLE_MANAGER', 'Project manager');
INSERT INTO role (code, name) VALUES ('ROLE_DEV', 'Developer');

-- Стартовые пользователи
-- 356a192b7913b04c54574d18c28d46e6395428ab - sha1 хэш пароля "1"
INSERT INTO account (nickname, passhash, role_id) VALUES ('Alex', '356a192b7913b04c54574d18c28d46e6395428ab', 1);
INSERT INTO account (nickname, passhash, role_id) VALUES ('Ben', '356a192b7913b04c54574d18c28d46e6395428ab', 2);
INSERT INTO account (nickname, passhash, role_id) VALUES ('Charlie', '356a192b7913b04c54574d18c28d46e6395428ab', 3);