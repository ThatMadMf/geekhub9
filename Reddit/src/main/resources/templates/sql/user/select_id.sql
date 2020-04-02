SELECT u.* , r.role
FROM reddit.users u
JOIN reddit.role_user ru
    ON ru.user_id = u.id
  JOIN reddit.roles r
    ON r.id = ru.role_id
 WHERE u.id = ?