package khumu.spring.batch.controller;

import khumu.spring.batch.config.TestConfig;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private final TestConfig testConfig;

    @GetMapping("/getapi")
    public String test() {
        return testConfig.getServer().get(0) + "/" + testConfig.getServer().get(1);
    }
}
