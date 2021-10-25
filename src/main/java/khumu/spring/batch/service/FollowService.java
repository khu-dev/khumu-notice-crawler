package khumu.spring.batch.service;

import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Follow;
import khumu.spring.batch.data.entity.User;
import khumu.spring.batch.repository.FollowRepository;
import khumu.spring.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void postNewFollow() {

    }

    public List<Author> getFollowByUserName(String username) {
        List<Follow> follows;
        User user = userRepository.findByUsername(username);

        follows = followRepository.findByFollower(user);

        for (Follow follow : follows) {

        }

        return
    }
}
