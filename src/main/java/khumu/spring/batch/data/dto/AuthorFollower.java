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
public class AuthorFollower {
    AuthorDto authorDto;
    List<UserDto> userDtos;
}
