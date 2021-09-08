package khumu.spring.batch.data.dto;

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
    String wholelink;
    Integer lastid;
    AuthorDto author;
}
