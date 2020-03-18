package org.geekhub.reddit.services;

import org.geekhub.reddit.db.dtos.Login;
import org.geekhub.reddit.db.dtos.UserDao;
import org.geekhub.reddit.db.dtos.UserDto;
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
        String sql = "insert into reddit.users (id, login, email, password, registration_date) " +
                "values (?, ?, ?, ?, ?)";

        UserDto userDto = new UserDto();

        userDto.setLogin(login.getLogin());
        userDto.setEmail(login.getEmail());
        userDto.setPassword(login.getPassword());
        userDto.setDate(LocalDate.now());
        userDto.setUserId(UUID.randomUUID());

        jdbcTemplate.update(sql, userDto.getUserId(), userDto.getLogin(), userDto.getEmail(), userDto.getPassword(),
                userDto.getDate());
    }
}
