package khumu.spring.batch.repository;

import khumu.spring.batch.data.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Page<Announcement> findByAuthor(Pageable pageable, String author);

    Page<Announcement> findByUpload_time(Pageable pageable, String date);

    List<Announcement> findByAuthor(@Param("author") String author);

    List<Announcement> findByUpload_time(@Param("upload_time") String upload_time);
    //    List<Announcement> findByName(@Param("author") String name);
//
//    List<Announcement> findByNameStartsWithIgnoreCase(String name);
//
//    List<Announcement> findByNameAndDescription(String name, String description);
}