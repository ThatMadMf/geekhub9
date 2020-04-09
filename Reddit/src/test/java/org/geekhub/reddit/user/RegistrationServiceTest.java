package org.geekhub.reddit.user;

import org.geekhub.reddit.exception.DataBaseRowException;
import org.geekhub.reddit.exception.RegistrationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class RegistrationServiceTest {

    private static final int SUCCESS_STATUS = 1;

    private UserRepository userRepository;
    private RegistrationService registrationService;

    @BeforeMethod
    public void setUp() {
        userRepository = mock(UserRepository.class);
        registrationService = new RegistrationService(userRepository);
    }

    @Test
    public void testLoadUserByUsername() {
        RedditUser redditUser = new RedditUser("LOGIN", LocalDate.now());
        PrivateRedditUser privateRedditUser = new PrivateRedditUser(
                "LOGIN",
                LocalDate.now(),
                "USER",
                "MAIL",
                "password"
        );

        when(userRepository.getUserByLogin("LOGIN")).thenReturn(redditUser);
        when(userRepository.getUserInfo(0)).thenReturn(privateRedditUser);

        UserDetails details = registrationService.loadUserByUsername("LOGIN");

        assertNotNull(details);
        assertEquals(details.getUsername(), "LOGIN");
        assertEquals(details.getPassword(), "password");
    }

    @Test
    public void testRegister() {
        RegistrationDto registrationDto = new RegistrationDto("login", "mail", "password");
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(userRepository.registerUser(registrationDto)).thenReturn(SUCCESS_STATUS);

        registrationService.register(httpServletRequest, registrationDto);
    }

    @Test(expectedExceptions = RegistrationException.class)
    public void throwing_exception_while_registration_with_not_unique_data() {
        when(userRepository.registerUser(any(RegistrationDto.class))).thenThrow(DataBaseRowException.class);

        registrationService.register(mock(HttpServletRequest.class), new RegistrationDto());
    }

    @Test(expectedExceptions = RegistrationException.class)
    public void throwing_exception_when_servlet_login_failed() throws ServletException {
        RegistrationDto registrationDto = new RegistrationDto("login", "mail", "password");
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(userRepository.registerUser(registrationDto)).thenReturn(SUCCESS_STATUS);
        doThrow(ServletException.class).when(httpServletRequest).login(anyString(), anyString());

        registrationService.register(httpServletRequest, registrationDto);
    }
}