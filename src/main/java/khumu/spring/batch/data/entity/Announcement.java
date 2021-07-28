package khumu.spring.batch.data.entity;

import khumu.spring.batch.data.entity.WebUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String sublink;
    private String date;

    @ManyToOne
    @JoinColumn(name = "weburl_id", foreignKey = @ForeignKey(name = "FK_WEBURL"))
    private String author;

    public Announcement(String title, String sublink, String date) {
        this.title = title;
        this.sublink = sublink;
        this.date = date;
    }
}
