package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByName(@Param("author") String name);

    List<Announcement> findByNameStartsWithIgnoreCase(String name);

    List<Announcement> findByNameAndDescription(String name, String description);
}