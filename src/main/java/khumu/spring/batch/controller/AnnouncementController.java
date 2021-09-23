package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.AnnouncementDto;
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
    public List<AnnouncementDto> getAnnouncementAll() {
        return announcementService.getAllAnnouncements();
    }

    @GetMapping("authorname")
    public List<AnnouncementDto> getAnnouncementByAuthor(@RequestParam String authorName) {
        return announcementService.getAnnouncementByAuthor(authorName);
    }

    @GetMapping("date")
    public List<AnnouncementDto> getAnnouncementByTime(@RequestParam String date) {
        return announcementService.getAnnouncementByDate(date);
    }

    @GetMapping("user")
    public List<AnnouncementDto> getAnnouncementByUser(@RequestParam String user) {
        return announcementService.getAnnouncementByUser(user);
    }
}