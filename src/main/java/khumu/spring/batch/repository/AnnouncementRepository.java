package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Page<Announcement> findAll(Pageable pageable);

    List<Announcement> findByAuthor(Author author);

    List<Announcement> findByAuthor(Author author, Pageable pageable);

    List<Announcement> findByDate(String date);

    List<Announcement> findByDate(String date, Pageable pageable);

    List<Announcement> findByTitleContaining(String keyword);

    List<Announcement> findByTitleContaining(String keyword, Pageable pageable);
}