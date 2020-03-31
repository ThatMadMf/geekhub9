package org.geekhub.reddit.dtos;

import javax.servlet.http.HttpServletRequest;

public interface UserDao {

    void register(HttpServletRequest httpServletRequest, RegistrationDto registrationDto);
}
