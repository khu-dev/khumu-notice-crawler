package khumu.spring.batch.service;

import khumu.spring.batch.data.DateTime;
import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.User;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository,
                               UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
    }

    public List<Announcement> getEveryAnnouncements() {
        return announcementRepository.findAll();
    }

    public List<Announcement> getAnnouncementByAuthor(Long authorname) {
        return announcementRepository.findByAuthor(authorname);
    }

    public List<Announcement> getAnnouncementByDate(String date) {
        return announcementRepository.findByDate(date);
    }

    public List<Announcement> getAnnouncementByUser(String user) {
        List<User> userdatas = userRepository.findByUsername(user);
        List<Long> userids = userdatas.forEach();
        List<Long> authorids = userids.forEach();

        List<Long> ret = announcementRepository.findByAuthor(authorids.forEach());
        return announcementRepository.findByAuthor();
    }
}
