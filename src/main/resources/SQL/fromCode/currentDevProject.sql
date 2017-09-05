-- текущий проект разработчика
SELECT p.id, p.name, p.complete, p.manager_id
FROM project p INNER JOIN developer d on p.id = d.project_id
WHERE d.account_id = ? AND p.complete = FALSE;

-- все проекты разработчика
SELECT p.id, p.name, p.complete, p.manager_id
FROM project p INNER JOIN developer d on p.id = d.project_id
WHERE d.account_id = ?;