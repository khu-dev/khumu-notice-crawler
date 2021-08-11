package khumu.spring.batch.controller;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.repository.AnnouncementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/announcement")
public class AnnouncementController {

    private AnnouncementRepository announcementRepository;

    @ResponseBody
    @GetMapping("/announcement/{target}")
    public List<Announcement> getAnnouncement(@PathVariable String target) {
        ArrayList<Announcement> announcements = new ArrayList<Announcement>();
        announcements = announcementRepository.findByAuthor(target);

        return announcements;
    }

}
