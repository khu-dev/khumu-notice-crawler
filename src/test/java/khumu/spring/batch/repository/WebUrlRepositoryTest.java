package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebUrlRepositoryTest {
    @Autowired
    private BoardRepository webUrlRepository;

    @Test
    void crud() {
        webUrlRepository.save(new Board());

        webUrlRepository.findAll().forEach(System.out::println);
    }
}