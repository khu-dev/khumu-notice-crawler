package khumu.spring.batch.data.dto;

import khumu.spring.batch.data.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String title;
    String subLink;
    String date;
    AuthorDto author;

    public Announcement toEntity() {
        return Announcement.builder()
                .id(id)
                .title(title)
                .date(date)
                .subLink(subLink)
                .author(author.toEntity())
                .build();
    }
}
