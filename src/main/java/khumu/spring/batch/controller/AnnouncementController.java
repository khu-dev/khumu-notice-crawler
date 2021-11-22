package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.AnnouncementDto;
import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AnnouncementRepository announcementRepository;

    @GetMapping("/all")
    public List<AnnouncementDto> getAnnouncementAll() {
        return announcementService.getAllAnnouncements();
    }

    @GetMapping("authorName")
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

    @GetMapping("/search")
    public List<AnnouncementDto> searchAnnouncement(@RequestParam String keyword) {
        return announcementService.searchAnnouncement(keyword);
    }
}