package khumu.spring.batch.controller;

import khumu.spring.batch.configuration.SnsConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    SnsConfiguration snsConfiguration;

    @Autowired
    private Environment environment;

    @GetMapping("/snsConfig")
    public String test() {
        return Arrays.toString(environment.getActiveProfiles());
    }
}
