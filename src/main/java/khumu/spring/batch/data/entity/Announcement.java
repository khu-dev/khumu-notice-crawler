package khumu.spring.batch.data.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Announcement")
@Table(name = "announcement")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     private String title;
     private String subLink;
     private String date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;
}
