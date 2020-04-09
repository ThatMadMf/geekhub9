package org.geekhub.reddit.user;

import org.geekhub.reddit.RedditMain;
import org.geekhub.reddit.db.configuration.DatabaseConfig;
import org.geekhub.reddit.exception.DataBaseRowException;
import org.geekhub.reddit.exception.NoRightsException;
import org.geekhub.reddit.post.Post;
import org.geekhub.reddit.subreddit.Subreddit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest(classes = {RedditMain.class, DatabaseConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserRepositoryTest extends AbstractTestNGSpringContextTests {

    private static final int SUCCESS_UPDATE_STATUS = 1;
    private static final int FAILED_UPDATE_STATUS = 0;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUserByLogin() {
        RedditUser redditUser = userRepository.getUserByLogin("user");

        assertEquals(redditUser.getLogin(), redditUser.getLogin());
        assertEquals(redditUser.getId(), redditUser.getId());
    }

    @Test
    public void testGetUserInfo() {
        PrivateRedditUser redditUser = userRepository.getUserInfo(5);

        assertEquals(redditUser.getLogin(), redditUser.getLogin());
        assertEquals(redditUser.getRole(), "USER");
        assertEquals(redditUser.getId(), redditUser.getId());
    }

    @Test
    public void testRegisterUser() {
        RegistrationDto registrationDto = new RegistrationDto("TEST_USER", "TEST_EMAIL", "pass");

        assertEquals(userRepository.registerUser(registrationDto), SUCCESS_UPDATE_STATUS);
    }

    @Test
    public void testGetSubscriptions() {
        List<Subreddit> subscribers = userRepository.getSubscriptions(2);

        assertEquals(subscribers.size(), 1);
        assertEquals(subscribers.get(0).getName(), "AskReddit");
    }

    @Test
    public void testGetPosts() {
        int userId = userRepository.getUserByLogin("admin").getId();
        List<Post> posts = userRepository.getPosts(userId);

        assertEquals(posts.size(), 1);
        assertEquals(posts.get(0).getTitle(), "Subreddit created");
    }

    @Test
    public void testDeleteUser() {
        int id = userRepository.getUserByLogin("dude").getId();
        assertEquals(userRepository.deleteUser(id), 1);
    }

    @Test
    public void testEditUser() {
        UserDto userDto = new UserDto("NEW LOGIN", "NEW EMAIL");

        PrivateRedditUser redditUser = userRepository.editUser(2, userDto);

        assertNotNull(redditUser);
        assertEquals(redditUser.getLogin(), "NEW LOGIN");
        assertEquals(redditUser.getId(), 2);
        assertEquals(redditUser.getEmail(), "NEW EMAIL");
    }

    @Test
    public void testEditUserRole() {
        Role initialRole = Role.valueOf(userRepository.getUserInfo(2).getRole());
        assertNotEquals(initialRole, Role.ADMIN);

        PrivateRedditUser redditUser = userRepository.editUserRole(2, Role.ADMIN);

        assertNotNull(redditUser);
        assertEquals(redditUser.getRole(), Role.ADMIN.name());
        assertEquals(redditUser.getId(), 2);
    }

    @Test(expectedExceptions = DataBaseRowException.class)
    public void throwing_exception_when_invalid_login() {
        userRepository.getUserByLogin("not|existing|login");
    }

    @Test(expectedExceptions = DataBaseRowException.class)
    public void throwing_exception_when_invalid_id() {
        userRepository.getUserInfo(-1);
    }

    @Test(expectedExceptions = NoRightsException.class)
    public void throwing_exception_when_deleting_super_admin() {
        int adminId = userRepository.getUserByLogin("admin").getId();
        String role = userRepository.getUserInfo(adminId).getRole();
        assertEquals(role, "SUPER_ADMIN");

        userRepository.deleteUser(adminId);
    }

    @Test(expectedExceptions = DataBaseRowException.class)
    public void throwing_exception_when_editing_user_by_invalid_id() {
        int notExistingId = -1;
        UserDto userDto = new UserDto("SHOULD", "FAIL");

        userRepository.editUser(notExistingId, userDto);
    }


    @Test(expectedExceptions = DataBaseRowException.class)
    public void throwing_exception_when_editing_user_role_by_invalid_id() {
        int notExistingId = -1;

        userRepository.editUserRole(notExistingId, Role.USER);
    }
}