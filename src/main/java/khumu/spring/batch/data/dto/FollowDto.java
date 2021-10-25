package khumu.spring.batch.data.dto;

import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {
    private String follower;
    private String followAuthor;
}
