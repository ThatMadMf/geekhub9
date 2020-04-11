SELECT u.* , r.role
FROM users u
JOIN role_user ru
    ON ru.user_id = u.id
  JOIN roles r
    ON r.id = ru.role_id
 WHERE u.id = ?