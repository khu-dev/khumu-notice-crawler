package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Follow;
import khumu.spring.batch.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower(User follower);

    List<Follow> findByFollowAuthor(Author author);

    List<Follow> findByFollowAuthorAndFollower(Author author, User user);
}
