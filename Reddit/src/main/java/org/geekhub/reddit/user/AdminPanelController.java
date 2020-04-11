package org.geekhub.reddit.user;

import org.geekhub.reddit.user.dto.PrivateRedditUser;
import org.geekhub.reddit.user.dto.UserDto;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("api/admin-panel")
public class AdminPanelController {

    private final UserService userService;

    public AdminPanelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user/{id}")
    public PrivateRedditUser getUserData(@PathVariable int id) {
        return userService.getUserPrivateData(id);
    }

    @PutMapping("user/{id}")
    public PrivateRedditUser editUser(@PathVariable int id, @RequestBody UserDto userDto) {
        return userService.editUser(id, userDto);
    }

    @Secured("ROLE_SUPER_ADMIN")
    @PutMapping("set-role/user/{id}")
    public PrivateRedditUser editUserRole(@PathVariable int id, @RequestBody Role role) {
        return userService.editUserRole(id, role);
    }
}
