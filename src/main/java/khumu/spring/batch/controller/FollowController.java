package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.dto.UserDto;
import khumu.spring.batch.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowController {
    @Autowired
    private final FollowService followService;

    @PostMapping("/postFollow")
    public void postFollow(@RequestParam(name = "userName") String userName, @RequestParam(name = "authorName") String authorName) {
        followService.postNewFollow(userName, authorName);
    }

    @GetMapping("user")
    public List<AuthorDto> getFollowByUserName(@RequestParam String userName) {
        return followService.getFollowByUserName(userName);
    }

    @DeleteMapping("/deleteFollow")
    public String deleteFollow(@RequestParam String userName, @RequestParam String authorName) {
        return followService.deleteFollow(userName, authorName);
    }

    @GetMapping("followAuthor")
    public List<UserDto> getUserByAuthor(@RequestParam String authorName) {
        return followService.getUserByFollow(authorName);
    }
}
