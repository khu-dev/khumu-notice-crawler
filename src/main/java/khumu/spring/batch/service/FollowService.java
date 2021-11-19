package khumu.spring.batch.service;

import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.dto.UserDto;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Follow;
import khumu.spring.batch.data.entity.User;
import khumu.spring.batch.repository.AuthorRepository;
import khumu.spring.batch.repository.FollowRepository;
import khumu.spring.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    public void postNewFollow(String userName, String authorName) {
        User user = userRepository.findByUsername(userName);
        Author author = authorRepository.findByAuthorName(authorName);

        Follow follow = Follow.builder()
                .followAuthor(author)
                .follower(user).build();

        followRepository.save(follow);
    }

    public List<AuthorDto> getFollowByUserName(String username) {
        User user = userRepository.findByUsername(username);
        List<Follow> follows = followRepository.findByFollower(user);
        List<AuthorDto> authorDtos = new ArrayList<>();

        for (Follow follow : follows) {
            AuthorDto authorDto = AuthorDto.builder()
                    .authorName(follow.getFollowAuthor().getAuthorName())
                    .followed(Boolean.TRUE)
                    .build();

            authorDtos.add(authorDto);
        }

        return authorDtos;
    }

    public void deleteFollow(String userName, String authorName) {
        User user = userRepository.findByUsername(userName);
        Author author = authorRepository.findByAuthorName(authorName);

        Follow follow = Follow.builder()
                .followAuthor(author)
                .follower(user).build();

        followRepository.delete(follow);
    }

    public List<UserDto> getUserByFollow(String authorName) {
        List<UserDto> userDtos = new ArrayList<>();
        List<Follow> follows = followRepository.findByFollowAuthor(authorRepository.findByAuthorName(authorName));
        System.out.println("Author name : " + authorName);
        System.out.println("AuthorRepository List : " + authorRepository.findByAuthorName(authorName));
        System.out.println("follows : " + follows);

        for(Follow follow : follows) {
            String username = follow.getFollower().getUsername();
            userDtos.add(UserDto.builder()
                    .username(username)
                    .build());
        }

        return userDtos;
    }
}
