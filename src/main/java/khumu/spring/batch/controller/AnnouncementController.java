package khumu.spring.batch.controller;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.WebUrl;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/all")
    public List<Announcement> getAnnouncementAll() {
        return announcementService.getAnnouncementsByAuthor();
    }

    @GetMapping("/author/{authorname}")
    public Announcement getAnnouncementByAuthor(@PathVariable String authorname) {
        return announcementService.getAnnouncementByAuthor(authorname);
    }

//    @GetMapping("/announcements/{dates}")
//    public List<Announcement> getAnnouncementByTime(@PathVariable String date) {
//        List<Announcement> selectedAnnouncements = announcementRepository.findByDate(date);
//        return selectedAnnouncements;
//    }
}
