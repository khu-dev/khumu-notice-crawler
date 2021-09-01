package khumu.spring.batch.controller;

import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @GetMapping("/all")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }
}
