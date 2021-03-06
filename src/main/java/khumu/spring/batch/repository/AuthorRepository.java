package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByAuthorName(String authorName);

    List<Author> findByAuthorNameContaining(String authorName);
}
