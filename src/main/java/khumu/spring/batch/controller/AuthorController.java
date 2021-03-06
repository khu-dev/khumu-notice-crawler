package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.dto.AuthorFollower;
import khumu.spring.batch.data.dto.UserDto;
import khumu.spring.batch.publish.EventPublish;
import khumu.spring.batch.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;
    private final EventPublish ep;

    @GetMapping("/all")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/authorFollower")
    public AuthorFollower getAuthorFollower(@RequestParam String authorName) {
        return authorService.getAuthorFollower(authorName);
    }
}
