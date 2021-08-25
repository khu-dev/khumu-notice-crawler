package khumu.spring.batch.service;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.repository.AnnouncementRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public Announcement getAnnouncementByAuthor(String authorname) {
        Announcement selectedAnnouncement = announcementRepository.findByAuthor(authorname);
        return selectedAnnouncement;
    }

    public List<Announcement> getAnnouncementsByAuthor() {
        return announcementRepository.findAll();
    }
}
