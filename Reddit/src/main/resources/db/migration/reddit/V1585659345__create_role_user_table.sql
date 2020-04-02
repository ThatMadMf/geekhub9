CREATE TABLE IF NOT EXISTS role_user (
    user_id         INTEGER NOT NULL REFERENCES users(id),
    role_id         INTEGER REFERENCES roles(id),
    CONSTRAINT      role_user_pkey PRIMARY KEY (user_id, role_id)
);
