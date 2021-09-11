package khumu.spring.batch.service;

import khumu.spring.batch.data.DateTime;
import khumu.spring.batch.data.dto.AnnouncementDto;
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
    private final AuthorRepository authorRepository;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository,
                               AuthorRepository authorRepository,
                               FollowRepository followRepository,
                               UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.authorRepository = authorRepository;
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public List<AnnouncementDto> getAllAnnouncements() {
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        List<Announcement> announcements = announcementRepository.findAll();
        for (Announcement announcement : announcements) {
            AnnouncementDto announcementDto = AnnouncementDto.builder()
                    .id(announcement.getId())
                    .title(announcement.getTitle())
                    .sublink(announcement.getSublink())
                    .date(announcement.getDate())
                    .author(announcement.getAuthor())
                    .build();
            announcementDtos.add(announcementDto);
        }
        return announcementDtos;
    }

    public List<AnnouncementDto> getAnnouncementByAuthor(Long authorname) {
        List<Announcement> announcements = announcementRepository.findByAuthor(authorname);
        List<AnnouncementDto> announcementDtos = new ArrayList<>();

        for (Announcement announcement : announcements) {
            AnnouncementDto announcementDto = AnnouncementDto.builder()
                    .id(announcement.getId())
                    .title(announcement.getTitle())
                    .sublink(announcement.getSublink())
                    .date(announcement.getDate())
                    .author(announcement.getAuthor())
                    .build();
            announcementDtos.add(announcementDto);
        }

        return announcementDtos;
    }

    public List<AnnouncementDto> getAnnouncementByDate(String date) {
        return announcementRepository.findByDate(date);
    }

    public List<AnnouncementDto> getAnnouncementByUser(String user) {

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
