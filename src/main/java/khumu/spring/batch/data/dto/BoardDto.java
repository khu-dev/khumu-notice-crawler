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
public class BoardDto {
    Long id;
    String wholeLink;
    Integer lastId;
    AuthorDto author;
}
