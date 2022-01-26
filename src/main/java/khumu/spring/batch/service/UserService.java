package khumu.spring.batch.service;

import khumu.spring.batch.data.entity.User;
import khumu.spring.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.GeneratedValue;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void postUser(String userName) {

        User user = new User();
        if (null == userRepository.findByUsername(userName)) {
            user = User.builder()
                    .username(userName).build();
            System.out.println("새로운 row 생성");
        }
        else {
            user = User.builder()
                    .username("새로운" + userName).build();
            System.out.println("동명이인발생");
        }

        userRepository.save(user);
        System.out.println("저장 성공!");
    }

}
