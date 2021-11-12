package khumu.spring.batch.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewAnnouncementCrawled {
    AnnouncementDto announcement;   // 방금 크롤링한 Announcement의 DTO 자체
    List<String> followers;         // 해당 공지사항 작성자에 대한 구독자들 (알림의 수신자가 된다.)
}
