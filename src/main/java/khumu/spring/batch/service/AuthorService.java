package khumu.spring.batch.service;

import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
