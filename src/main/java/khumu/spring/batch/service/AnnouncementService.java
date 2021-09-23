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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Announcement> announcements = announcementRepository.findAll();
        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> getAnnouncementByAuthor(String authorname) {
        Author author = authorRepository.findByAuthorname(authorname);
        List<Announcement> announcements = announcementRepository.findByAuthor(author);
        return getAnnouncementDtos(announcements);
    }

    private List<AnnouncementDto> getAnnouncementDtos(List<Announcement> announcements) {
        List<AnnouncementDto> announcementDtos = new ArrayList<>();

        return getAnnouncementDtos(announcements, announcementDtos);
    }

    private List<AnnouncementDto> getAnnouncementDtos(List<Announcement> announcements, List<AnnouncementDto> announcementDtos) {
        for (Announcement announcement : announcements) {
            AuthorDto authorDto = AuthorDto.builder()
                    .id(announcement.getId())
                    .author_name(announcement.getAuthor().getAuthorname())
                    .followed(Boolean.FALSE)
                    .build();

            AnnouncementDto announcementDto = AnnouncementDto.builder()
                    .id(announcement.getId())
                    .title(announcement.getTitle())
                    .sub_link(announcement.getSublink())
                    .date(announcement.getDate())
                    .author(authorDto)
                    .build();
            announcementDtos.add(announcementDto);
        }
        return announcementDtos;
    }

    public List<AnnouncementDto> getAnnouncementByDate(String date) {
        List<Announcement> announcements = announcementRepository.findByDate(date);
        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> getAnnouncementByUser(String user) {

        // 1단계
        // User Id 뽑아서 Follow 명단 찾기
        User userdata = userRepository.findByUsername(user);
        Long userdataId = userdata.getId();
        System.out.println(userdataId);

        // 입력된 user에 따른 팔로우 목록 리스트
        List<Follow> follows = followRepository.findByFollower(userdata);

        // 2단계
        // follow->followauthor == announcemnt->author 같아야 함
        List<Announcement> announcements = new ArrayList<>();
        List<AnnouncementDto> announcementDtos = new ArrayList<>();

        for (Follow follow : follows) {
            Long followAuthor = follow.getFollowauthor().getId();
            Author author = authorRepository.findById(followAuthor).orElse(null);

            List<Announcement> announcementList = announcementRepository.findByAuthor(author);
            announcements.addAll(announcementList);
        }

        return getAnnouncementDtos(announcements, announcementDtos);
    }
}
