package org.geekhub.reddit.user;

import org.geekhub.reddit.exception.RegistrationException;
import org.geekhub.reddit.user.dto.PrivateRedditUser;
import org.geekhub.reddit.user.dto.RegistrationDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Service
public class RegistrationService implements UserDetailsService {

    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        PrivateRedditUser user = findUserByLogin(login);

        User.UserBuilder builder;
        builder = org.springframework.security.core.userdetails.User.withUsername(user.getLogin());
        builder.password(user.getPassword());
        builder.roles(user.getRole());
        return builder.build();
    }

    public void register(HttpServletRequest request, RegistrationDto registrationDto) {
        try {
            userRepository.registerUser(registrationDto);
            authWithHttpServletRequest(request, registrationDto.getLogin(), registrationDto.getPassword());
        } catch (Exception ex) {
            throw new RegistrationException(ex.getMessage());
        }
    }

    private void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            throw new RegistrationException("Cannot login");
        }
    }


    private PrivateRedditUser findUserByLogin(String login) {
        return userRepository.getUserInfo(userRepository.getUserByLogin(login).getId());
    }
}
