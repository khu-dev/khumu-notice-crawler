package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.FollowDto;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/post")
    public void postFollow(@RequestBody FollowDto requestData) {
        followService.postNewFollow();
    }

    @GetMapping("/user")
    public List<Author> getFollowByUserName(String userName) {
        return followService.getFollowByUserName(userName);
    }
}
