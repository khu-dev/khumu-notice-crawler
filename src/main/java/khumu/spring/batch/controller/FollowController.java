package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.dto.FollowDto;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/post")
    public void postFollow(@RequestParam(name = "userName") String userName, @RequestParam(name = "authorName") String authorName) {
        followService.postNewFollow(userName, authorName);
    }

    @GetMapping("user")
    public List<AuthorDto> getFollowByUserName(@RequestParam String userName) {
        return followService.getFollowByUserName(userName);
    }
}
