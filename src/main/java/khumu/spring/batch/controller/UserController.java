package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.UserDto;
import khumu.spring.batch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public void postUser(@RequestParam String userName) {
        userService.postUser(userName);
    }

//    @GetMapping("/art")
//    public String test() {
//
//        String target = "http://and.khu.ac.kr/board/bbs/board.php?bo_table=05_01";
//        Document document = Jsoup.connect(target).get();
//
//        Elements elements = document.select("td_subject");
//
//        Iterator<Element> ie1 = elements.select()
//
//        return titles;
//    }
}
