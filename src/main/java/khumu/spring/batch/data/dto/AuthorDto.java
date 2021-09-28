package khumu.spring.batch.data.dto;

import khumu.spring.batch.data.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    Long id;
    String author_name;
    Boolean followed;

    public Author toEntity() {
        return Author.builder()
                .authorName(author_name)
                .build();
    }
}
