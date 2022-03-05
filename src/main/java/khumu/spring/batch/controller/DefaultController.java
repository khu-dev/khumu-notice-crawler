package khumu.spring.batch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class DefaultController {
    @GetMapping("error")
    public String error() {
        return "API문서를 참고하세요.";
    }
}
