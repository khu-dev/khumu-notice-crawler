package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.WebUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebUrlRepository extends JpaRepository<WebUrl, Long> {
}
