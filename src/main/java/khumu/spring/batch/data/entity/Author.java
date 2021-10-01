package khumu.spring.batch.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
    @JsonBackReference
//    @JsonIgnoreProperties({"author", "announcements"})
    private List<Board> boards = new ArrayList<Board>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
    @JsonBackReference
//    @JsonIgnoreProperties({"author", "boards"})
    private List<Announcement> announcements = new ArrayList<Announcement>();
}
