CREATE TABLE public.role
(
  id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(32) NOT NULL
);

CREATE TABLE public.account
(
  id SERIAL PRIMARY KEY NOT NULL,
  nickname VARCHAR(32) NOT NULL,
  passhash VARCHAR(32) NOT NULL,
  role_id INT NOT NULL,
  CONSTRAINT account_role_id_fk FOREIGN KEY (role_id) REFERENCES role (id) ON UPDATE CASCADE
);
CREATE UNIQUE INDEX account_nickname_uindex ON public.account (nickname);

CREATE TABLE public.project
(
  id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(32) NOT NULL,
  complete BOOLEAN NOT NULL,
  manager_id INT NOT NULL,
  CONSTRAINT project_account_id_fk FOREIGN KEY (manager_id) REFERENCES account (id) ON UPDATE CASCADE
);

CREATE TABLE public.developer
(
  account_id INT NOT NULL,
  project_id INT PRIMARY KEY NOT NULL,
  CONSTRAINT developer_account_id_fk FOREIGN KEY (account_id) REFERENCES account (id) ON UPDATE CASCADE,
  CONSTRAINT developer_project_id_fk FOREIGN KEY (project_id) REFERENCES project (id) ON UPDATE CASCADE
);