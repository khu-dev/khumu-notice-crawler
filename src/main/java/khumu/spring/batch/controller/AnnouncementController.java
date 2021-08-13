package khumu.spring.batch.controller;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.repository.AnnouncementRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/announcement")
public class AnnouncementController {

    private AnnouncementRepository announcementRepository;

    @ResponseBody
    @GetMapping("/announcement/{target}")
    public ArrayList<Announcement> getAnnouncementByAuthor(@PathVariable String target) {
        ArrayList<Announcement> announcements = new ArrayList<Announcement>();
        PageRequest pageRequest = PageRequest.of(1, 1);

        announcements = announcementRepository.findByAuthor(target);

        return announcements;
    }

    @ResponseBody
    @GetMapping("/announcement/{time}")
    public ArrayList<Announcement> getAnnouncementByTime(@PathVariable String Date) {
        ArrayList<Announcement> announcements = new ArrayList<Announcement>();
        PageRequest pageRequest = PageRequest.of(1, 1);

        announcements = announcementRepository.findByDate(Date);

        return announcements;
    }

}
