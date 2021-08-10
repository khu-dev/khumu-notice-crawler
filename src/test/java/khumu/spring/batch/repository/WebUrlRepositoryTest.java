package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.WebUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebUrlRepositoryTest {
    @Autowired
    private WebUrlRepository webUrlRepository;

    @Test
    void crud() {
        webUrlRepository.save(new WebUrl());

        webUrlRepository.findAll().forEach(System.out::println);
    }
}