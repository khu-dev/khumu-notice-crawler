package khumu.spring.batch.controller;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.repository.AnnouncementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/announcement")
public class AnnouncementController {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @GetMapping("/{target}")
    public Optional<Announcement> getAnnouncementByAuthor(@PathVariable String target) {
        Optional<Announcement> selectedAnnouncements = announcementRepository.findByAuthor(target);
        return selectedAnnouncements;
//        ArrayList<Announcement> announcements = new ArrayList<Announcement>();
//        PageRequest pageRequest = PageRequest.of(1, 1);
//
//        announcements = announcementRepository.findByAuthor(target);
//
//        return announcements;
    }

    @GetMapping("/{time}")
    public Optional<Announcement> getAnnouncementByTime(@PathVariable String date) {
        Optional<Announcement> selectedAnnouncements = announcementRepository.findByDate(date);
        return selectedAnnouncements;
    }
}
