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

    @GetMapping("authorname")
    public List<AnnouncementDto> getAnnouncementByAuthor(@RequestParam String authorName) {
        return announcementService.getAnnouncementByAuthor(authorName);
    }

    @GetMapping("date")
    public List<AnnouncementDto> getAnnouncementByTime(@RequestParam String date) {
        return announcementService.getAnnouncementByDate(date);
    }

    @PostMapping("/test")
    public void posttest() {
        announcementRepository.save(Announcement.builder()
                .title("메렁")
                .subLink("ㅁㄴ이ㅏ럼닝ㄹ")
                .date("2021-09-29 12:53:00")
                .author(Author.builder()
                        .authorName("hello")
                        .id(1L)
                        .build())
                .build());
    }

    @GetMapping("user")
    public List<AnnouncementDto> getAnnouncementByUser(@RequestParam String user) {
        return announcementService.getAnnouncementByUser(user);
    }
}