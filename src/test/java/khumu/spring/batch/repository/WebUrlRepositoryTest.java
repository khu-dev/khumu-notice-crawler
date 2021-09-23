package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class boardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void crud() {
        boardRepository.save(new Board());

        boardRepository.findAll().forEach(System.out::println);
    }
}