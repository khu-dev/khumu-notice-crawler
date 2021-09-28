package khumu.spring.batch.data.dto;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {
    Long id;
    String title;
    String sub_link;
    String date;
    AuthorDto author;

    public Announcement toEntity() {
        return Announcement.builder()
                .title(title)
                .date(date)
                .subLink(sub_link)
                .author(author.toEntity())
                .build();
    }
}
