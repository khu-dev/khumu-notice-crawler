package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.UserDto;
import khumu.spring.batch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public void postUser(@RequestParam String userName) {
        userService.postUser(userName);
    }
}
