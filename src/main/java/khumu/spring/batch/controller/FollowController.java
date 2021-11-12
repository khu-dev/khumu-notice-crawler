package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.dto.FollowDto;
import khumu.spring.batch.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/postfollow")
    public void postFollow(@RequestParam(name = "userName") String userName, @RequestParam(name = "authorName") String authorName) {
        followService.postNewFollow(userName, authorName);
    }

    @GetMapping("user")
    public List<AuthorDto> getFollowByUserName(@RequestParam String userName) {
        return followService.getFollowByUserName(userName);
    }

    @DeleteMapping("/deletefollow")
    public void deleteFollow(@RequestParam String userName, @RequestParam String authorName) {
        followService.deleteFollow(userName, authorName);
    }

//    @GetMapping("followauthor")
//    public List<FollowDto>
}
