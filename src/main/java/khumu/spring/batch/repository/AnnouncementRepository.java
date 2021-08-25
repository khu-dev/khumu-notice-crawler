package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.WebUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Announcement findByAuthor(String author);

//    Optional<Announcement> findByDate(String date);

    //    List<Announcement> findByName(@Param("author") String name);
//
//    List<Announcement> findByNameStartsWithIgnoreCase(String name);
//
//    List<Announcement> findByNameAndDescription(String name, String description);
}