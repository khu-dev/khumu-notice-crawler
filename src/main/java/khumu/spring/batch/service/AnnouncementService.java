package khumu.spring.batch.service;

import khumu.spring.batch.data.dto.AnnouncementDto;
import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Follow;
import khumu.spring.batch.data.entity.User;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.AuthorRepository;
import khumu.spring.batch.repository.FollowRepository;
import khumu.spring.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AuthorRepository authorRepository;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    private List<AnnouncementDto> getAnnouncementDtos(List<Announcement> announcements) {
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        for (Announcement announcement : announcements) {
            AuthorDto authorDto = AuthorDto.builder()
                    .id(announcement.getId())
                    .authorName(announcement.getAuthor().getAuthorName())
                    .followed(Boolean.TRUE)
                    .build();

            AnnouncementDto announcementDto = AnnouncementDto.builder()
                    .id(announcement.getId())
                    .title(announcement.getTitle())
                    .subLink(announcement.getSubLink())
                    .date(announcement.getDate())
                    .author(authorDto)
                    .build();
            announcementDtos.add(announcementDto);
        }
        return announcementDtos;
    }

    public List<AnnouncementDto> getAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAll();
        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> getAllAnnouncements(Pageable pageable) {
        List<Announcement> announcements = announcementRepository.findAll(pageable).toList();
        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> getAnnouncementByAuthor(String authorName) {
        Author author = authorRepository.findByAuthorName(authorName);
        List<Announcement> announcements = announcementRepository.findByAuthor(author);
        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> getAnnouncementByAuthor(String authorName, Pageable pageable) {
        Author author = authorRepository.findByAuthorName(authorName);
        List<Announcement> announcements = announcementRepository.findByAuthor(author, pageable);
        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> getAnnouncementByDate(String date) {
        List<Announcement> announcements = announcementRepository.findByDate(date);
        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> getAnnouncementByDate(String date, Pageable pageable) {
        List<Announcement> announcements = announcementRepository.findByDate(date, pageable);
        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> getAnnouncementByUser(String user) {
        // 1단계
        // User Id 뽑아서 Follow 명단 찾기
        User userData = userRepository.findByUsername(user);
        Long userDataId = userData.getId();
        System.out.println(userDataId);

        // 입력된 user 팔로우 목록 리스트
        List<Follow> follows = followRepository.findByFollower(userData);

        // 2단계
        // follow->followAuthor == announcement->author 같아야 함
        List<Announcement> announcements = new ArrayList<>();

        for (Follow follow : follows) {
            Long followAuthor = follow.getFollowAuthor().getId();
            Author author = authorRepository.findById(followAuthor).orElse(null);

            List<Announcement> announcementList = announcementRepository.findByAuthor(author);
            announcements.addAll(announcementList);
        }

        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> getAnnouncementByUser(String user, Pageable pageable) {
        // 1단계
        // User Id 뽑아서 Follow 명단 찾기
        User userData = userRepository.findByUsername(user);
        Long userDataId = userData.getId();
        System.out.println(userDataId);

        // 입력된 user 팔로우 목록 리스트
        List<Follow> follows = followRepository.findByFollower(userData);

        // 2단계
        // follow->followAuthor == announcement->author 같아야 함
        List<Announcement> announcements = new ArrayList<>();

        for (Follow follow : follows) {
            Long followAuthor = follow.getFollowAuthor().getId();
            Author author = authorRepository.findById(followAuthor).orElse(null);

            List<Announcement> announcementList = announcementRepository.findByAuthor(author, pageable);
            announcements.addAll(announcementList);
        }

        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> searchAnnouncement(String keyword) {
        List<Announcement> announcements = announcementRepository.findByTitleContaining(keyword);
        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> searchAnnouncement(String keyword, Pageable pageable) {
        List<Announcement> announcements = announcementRepository.findByTitleContaining(keyword, pageable);
        return getAnnouncementDtos(announcements);
    }
}
