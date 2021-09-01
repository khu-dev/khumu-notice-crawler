package khumu.spring.batch.controller;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/all")
    public List<Announcement> getAnnouncementAll() {
        return announcementService.getEveryAnnouncements();
    }

    @GetMapping("/{authorname}")
    public List<Announcement> getAnnouncementByAuthor(@RequestParam Long authorname) {
        return announcementService.getAnnouncementByAuthor(authorname);
    }

    @GetMapping("/{date}")
    public List<Announcement> getAnnouncementByTime(@RequestParam String date) {
        return announcementService.getAnnouncementByDate(date);
    }

    @GetMapping("/{user}")
    public List<Announcement> getAnnouncementByUser(@RequestParam String user) {
        return announcementService.getAnnouncementByUser(user);
    }
}