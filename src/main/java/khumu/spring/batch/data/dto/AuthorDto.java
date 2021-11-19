package khumu.spring.batch.data.dto;

import khumu.spring.batch.data.entity.Author;
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
public class AuthorDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String authorName;
    Boolean followed;

    public Author toEntity() {
        return Author.builder()
                .id(id)
                .authorName(authorName)
                .build();
    }
}
