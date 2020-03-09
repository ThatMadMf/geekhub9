package org.geekhub.reddit.db.models;

public interface UserDao {

    void register(Login login);

    UserDto retrieveUser(String login);
}