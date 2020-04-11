package org.geekhub.reddit.user.dto;

public class UserDto {

    private String login;

    private String email;


    public UserDto() {

    }

    public UserDto(String login, String email) {
        this.login = login;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
