package khumu.spring.batch.service;

import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> authorDtos = new ArrayList<>();

        for (Author author : authors) {
            AuthorDto authorDto = AuthorDto.builder()
                    .id(author.getId())
                    .authorName(author.getAuthorName())
                    .build();
            authorDtos.add(authorDto);
        }
        return authorDtos;
    }
}
