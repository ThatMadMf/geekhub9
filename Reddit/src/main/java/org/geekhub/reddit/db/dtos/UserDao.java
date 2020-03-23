package org.geekhub.reddit.db.dtos;

import javax.servlet.http.HttpServletRequest;

public interface UserDao {

    void register(HttpServletRequest httpServletRequest, RegistrationDto registrationDto);
}
