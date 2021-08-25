package khumu.spring.batch.controller;

import khumu.spring.batch.data.entity.WebUrl;
import khumu.spring.batch.repository.WebUrlRepository;
import khumu.spring.batch.service.WebUrlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(value="/weburl")
public class WebUrlController {

    private final WebUrlService webUrlService;

    public WebUrlController(WebUrlService webUrlService) {
        this.webUrlService = webUrlService;
    }

    @GetMapping("/webUrlTest")
    public String webUrlTest() {
        return "it's working";
    }

//    @GetMapping("/weburl")
//    public WebUrl getWebUrl() {
//        WebUrl weburl = webUrlRepository.findByLastID(2330);
//        return weburl;
//    }
}
