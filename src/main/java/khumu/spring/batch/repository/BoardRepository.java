package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByLastId(Integer lastId);

    Optional<Board> findByAuthor(Author Author);

    Board findByAuthorId(Long author_Id);
}
