package khumu.spring.batch.controller;

import khumu.spring.batch.config.SnsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    SnsConfig snsConfig;

    @GetMapping("/snsConfig")
    public String test() {
        return snsConfig.getDev() + snsConfig.getLocal();
    }
}
