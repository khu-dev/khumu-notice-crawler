package khumu.spring.batch.service;

import khumu.spring.batch.data.DateTime;
import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Follow;
import khumu.spring.batch.data.entity.User;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.AuthorRepository;
import khumu.spring.batch.repository.FollowRepository;
import khumu.spring.batch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository,
                               FollowRepository followRepository,
                               UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.followRepository = followRepository;
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

        // 1단계
        // User Id 뽑아서 Follow 명단 찾기
        User userdatas = userRepository.findByUsername(user);
        Long follower = userdatas.getId();
        List<Follow> followedAuthorId = followRepository.findByFollower(follower);

        // 2단계
        // follow->followauthor == announcemnt->author 같아야 함
        List<Announcement> announcements = new ArrayList<>();
        for (Follow follow : followedAuthorId) {
            announcements.addAll(announcementRepository.findByAuthor(follow.getFollowauthor().getId()));
        }

        return announcements;
    }
}
