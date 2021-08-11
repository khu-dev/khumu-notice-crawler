package khumu.spring.batch.data.entity;

import khumu.spring.batch.data.entity.WebUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "announcement")
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sublink")
    private String sublink;

    @Column(name= "upload_time")
    private String upload_time;

    @ManyToOne
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "FK_WEBURL"))
    private WebUrl author;

    public Announcement(String title, String sublink, String upload_time) {
        this.title = title;
        this.sublink = sublink;
        this.upload_time = upload_time;
    }

    public String getAnnouncement() {
        String retData = title + "#" + upload_time + "#" + sublink;
        return retData;
    }
}
