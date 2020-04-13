UPDATE role_user SET role_id = (SELECT id FROM roles WHERE role = ?)
WHERE user_id = ?;
