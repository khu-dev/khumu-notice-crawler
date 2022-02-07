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
import java.util.stream.Collectors;

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
                    .followed(Boolean.FALSE)
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

    private List<AnnouncementDto> getAnnouncementDtos(List<Announcement> announcements, List<Author> followingAuthors) {
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        for (Announcement announcement : announcements) {
            AuthorDto authorDto = AuthorDto.builder()
                    .id(announcement.getId())
                    .authorName(announcement.getAuthor().getAuthorName())
                    .followed(Boolean.FALSE)
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

    public List<AnnouncementDto> getAllAnnouncements(String userName, Pageable pageable) {
        List<Announcement> announcements = announcementRepository.findAll(pageable).toList();
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        List<Follow> follows = followRepository.findByFollower(userRepository.findByUsername(userName).get());

        ArrayList<Long> authorIds = new ArrayList<>();

        for (Follow follow : follows) {
            authorIds.add(follow.getFollowAuthor().getId());
        }

        for (Announcement announcement : announcements) {
            Boolean isFollowed = authorIds.contains(announcement.getAuthor().getId());

            AuthorDto authorDto = AuthorDto.builder()
                    .id(announcement.getId())
                    .authorName(announcement.getAuthor().getAuthorName())
                    .followed(isFollowed)
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

    public List<AnnouncementDto> getAnnouncementByAuthor(String authorName, Pageable pageable) {
        Author author = authorRepository.findByAuthorName(authorName).get();
        List<Announcement> announcements = announcementRepository.findByAuthor(author, pageable);
        return getAnnouncementDtos(announcements);
    }

    public List<AnnouncementDto> getAnnouncementByUser(String userName, Pageable pageable) {
        // User Id 뽑아서 Follow 명단 찾기
        User userData = userRepository.findByUsername(userName).get();
        Long userDataId = userData.getId();
        System.out.println(userDataId);

        // 입력된 user 팔로우 목록 리스트
        List<Follow> follows = followRepository.findByFollower(userData);

        List<Author> followingAuthors = followRepository.findByFollower(userData).stream().map(Follow::getFollowAuthor).collect(Collectors.toList());

        List<Announcement> followingAnnouncements = announcementRepository.findByAuthorIn(followingAuthors, pageable);

        return getAnnouncementDtos(followingAnnouncements);
    }

    public List<AnnouncementDto> searchAnnouncement(String keyword ,Pageable pageable) {
        List<Announcement> announcements = announcementRepository.findByTitleContainingOrAuthor_AuthorNameContaining(keyword, keyword, pageable);
        return getAnnouncementDtos(announcements);
    }
}
