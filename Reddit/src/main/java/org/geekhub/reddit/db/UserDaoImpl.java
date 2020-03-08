package org.geekhub.reddit.db;

import org.geekhub.reddit.db.models.Login;
import org.geekhub.reddit.db.models.UserDao;
import org.geekhub.reddit.db.models.UserDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void register(Login login) {
        String sql = "insert into reddit.users (login, email, password, registration_date, link ) " +
                "values (?, ?, ?, ?, ?)";

        UserDto userDto = new UserDto();

        userDto.setLogin(login.getLogin());
        userDto.setEmail(login.getEmail());
        userDto.setPassword(login.getPassword());
        userDto.setDate(LocalDate.now());
        userDto.setUserLink(UUID.randomUUID());

        jdbcTemplate.update(sql, userDto.getLogin(), userDto.getEmail(), userDto.getPassword(),
                userDto.getDate(), userDto.getUserLink());
    }

    @Override
    public UserDto retrieveUser(String login) {
        return null;
    }
}
