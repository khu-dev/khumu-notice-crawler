package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.WebUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebUrlRepository extends JpaRepository<WebUrl, Long> {
    WebUrl findByLastid(Integer lastid);
}
