package khumu.spring.batch.data.entity;

import khumu.spring.batch.data.entity.WebUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Announcement")
@Table(name = "announcement")
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     private String title;
     private String sublink;
     private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "FK_WEBURL"))
    private WebUrl author;

    public Announcement(String title, String sublink, String date) {
        this.title = title;
        this.sublink = sublink;
        this.date = date;
    }

    public String getAnnouncement() {
        String retData = title + "#" + date + "#" + sublink;
        return retData;
    }
}
