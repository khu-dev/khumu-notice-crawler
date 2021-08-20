package khumu.spring.batch.controller;

import khumu.spring.batch.data.entity.WebUrl;
import khumu.spring.batch.repository.WebUrlRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(value="/api/weburl")
public class WebUrlController {

    private WebUrlRepository webUrlRepository;

    public WebUrlController(WebUrlRepository webUrlRepository) {
        this.webUrlRepository = webUrlRepository;
    }

    @GetMapping("/weburl")
    public Optional<WebUrl> getWebUrl() {
        Optional<WebUrl> weburls = webUrlRepository.findById(1l);
        return weburls;
    }
}
