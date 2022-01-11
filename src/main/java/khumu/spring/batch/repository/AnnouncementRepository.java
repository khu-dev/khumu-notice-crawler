package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByAuthor(Author author);

    List<Announcement> findByDate(String dateTime);

    List<Announcement> findByTitleContaining(String keyword);

    List<Announcement> findAll(Pageable pageable);
}