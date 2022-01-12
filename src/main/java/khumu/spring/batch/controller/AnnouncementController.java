package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.AnnouncementDto;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AnnouncementRepository announcementRepository;

    // 공지사항 전체 조회
    @GetMapping("/all")
    public List<AnnouncementDto> getAnnouncementAllPageable(@PageableDefault(size = 10, sort="date", direction = Sort.Direction.DESC) Pageable pageable) {
        return announcementService.getAllAnnouncements(pageable);
    }

    // 공지사항 작성자로 조회
    @GetMapping("/authorName")
    public List<AnnouncementDto> getAnnouncementByAuthor(@RequestParam String authorName, @PageableDefault(size = 10, sort="date", direction = Sort.Direction.DESC) Pageable pageable) {
        return announcementService.getAnnouncementByAuthor(authorName, pageable);
    }

//    @GetMapping("/user")
//    public List<AnnouncementDto> getAnnouncementByUser(@RequestParam String user) {
//        return announcementService.getAnnouncementByUser(user);
//    }

    //
    @GetMapping("/user")
    public List<AnnouncementDto> getAnnouncementByUser(@RequestParam String userName, @PageableDefault(size = 10, sort="date", direction = Sort.Direction.DESC) Pageable pageable) {
        return announcementService.getAnnouncementByUser(userName, pageable);
    }

    @GetMapping("/search")
    public List<AnnouncementDto> searchAnnouncement(@RequestParam String keyword, @PageableDefault(size = 10, sort="date", direction = Sort.Direction.DESC) Pageable pageable) {
        return announcementService.searchAnnouncement(keyword, pageable);
    }
}