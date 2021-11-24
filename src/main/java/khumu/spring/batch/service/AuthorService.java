package khumu.spring.batch.service;

import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.dto.AuthorFollower;
import khumu.spring.batch.data.dto.UserDto;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Follow;
import khumu.spring.batch.repository.AuthorRepository;
import khumu.spring.batch.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final FollowRepository followRepository;

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

    public AuthorFollower getAuthorFollower(String authorName) {
        Author author = authorRepository.findByAuthorName(authorName);
        List<Follow> follows = followRepository.findByFollowAuthor(author);
        List<String> followedNameList = new ArrayList<>();

        follows.forEach(c -> followedNameList.add(c.getFollower().getUsername()));

        List<UserDto> userDtos = new ArrayList<>();

        for (String followedName : followedNameList) {
            userDtos.add(UserDto.builder().username(followedName).build());
        }

        AuthorFollower authorFollower = AuthorFollower.builder()
                .authorDto(AuthorDto.builder()
                        .id(author.getId())
                        .authorName(author.getAuthorName())
                        .build())
                .userDtos(userDtos)
                .build();

        return authorFollower;
    }
}
