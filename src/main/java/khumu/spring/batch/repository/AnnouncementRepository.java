package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByAuthor(Author author, Pageable pageable);

    List<Announcement> findByAuthorIn(List<Author> authors, Pageable pageable);

    List<Announcement> findByTitleContainingOrAuthor_AuthorNameContaining(String keyword, String authorName, Pageable pageable);
}