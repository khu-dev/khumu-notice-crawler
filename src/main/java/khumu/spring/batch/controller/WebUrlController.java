package khumu.spring.batch.controller;

import khumu.spring.batch.data.entity.WebUrl;
import khumu.spring.batch.repository.WebUrlRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/weburl")
public class WebUrlController {

    private WebUrlRepository webUrlRepository;

    @ResponseBody
    @GetMapping("/sdf")
    public WebUrl getWebUrl() {
        WebUrl weburl = new WebUrl();

        return  weburl;
    }
}
