package org.geekhub.reddit.web.configuration;

import org.geekhub.reddit.db.repositories.UserRepository;
import org.geekhub.reddit.dtos.RegistrationDto;
import org.geekhub.reddit.exception.RegistrationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        RegistrationDto user = findUserById(login);

        User.UserBuilder builder;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(user.getLogin());
            builder.password(user.getPassword());
            builder.roles("USER");
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    public void register(HttpServletRequest request, RegistrationDto registrationDto) {
        try {
            userRepository.registerUser(registrationDto);
        } catch (Exception ex) {
            throw new RegistrationException("Login or email is taken. Try another one");
        }
        authWithHttpServletRequest(request, registrationDto.getLogin(), registrationDto.getPassword());
    }

    private void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            throw new RegistrationException("Cannot login");
        }
    }


    private RegistrationDto findUserById(String login) {
        return userRepository.getUserInfo(userRepository.getUserByLogin(login).getId());
    }
}
