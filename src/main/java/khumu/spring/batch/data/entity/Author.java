package khumu.spring.batch.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorname;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @JsonBackReference
//    @JsonIgnoreProperties({"author", "announcements"})
    private List<Board> boards = new ArrayList<Board>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @JsonBackReference
//    @JsonIgnoreProperties({"author", "boards"})
    private List<Announcement> announcements = new ArrayList<Announcement>();
}
