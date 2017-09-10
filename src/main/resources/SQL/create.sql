CREATE TABLE public.role
(
  id   SERIAL PRIMARY KEY NOT NULL,
  code VARCHAR(20)        NOT NULL,
  name VARCHAR(32)        NOT NULL
);

CREATE TABLE public.account
(
  id       SERIAL PRIMARY KEY NOT NULL,
  nickname VARCHAR(40)        NOT NULL,
  passhash VARCHAR(40)        NOT NULL,
  role_id  INT                NOT NULL,
  CONSTRAINT account_role_id_fk FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE UNIQUE INDEX account_nickname_uindex
  ON public.account (nickname);

CREATE TABLE public.project
(
  id            SERIAL PRIMARY KEY NOT NULL,
  name          VARCHAR(40)        NOT NULL,
  complete      BOOLEAN            NOT NULL,
  manager_id    INT                NOT NULL,
  start         TIMESTAMP          NOT NULL,
  estimated_end TIMESTAMP          NOT NULL,
  end_          TIMESTAMP,
  CONSTRAINT project_account_id_fk FOREIGN KEY (manager_id) REFERENCES account (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.developer
(
  id         SERIAL PRIMARY KEY NOT NULL,
  account_id INT                NOT NULL,
  project_id INT                NOT NULL,
  CONSTRAINT developer_account_id_fk FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT developer_project_id_fk FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.commentary
(
  id         SERIAL PRIMARY KEY NOT NULL,
  author_id  INT                NOT NULL,
  project_id INT                NOT NULL,
  text       VARCHAR(200)       NOT NULL,
  time         TIMESTAMP          NOT NULL,
  CONSTRAINT developer_project_id_fk FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT author_account_id_fk FOREIGN KEY (author_id) REFERENCES account (id) ON DELETE CASCADE ON UPDATE CASCADE
)