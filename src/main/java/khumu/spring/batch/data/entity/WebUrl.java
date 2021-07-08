package khumu.spring.batch.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "weburl")
@Entity(name = "WebUrl")
@NoArgsConstructor
public class WebUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;
    private String url;
    private int lastid;

    @OneToMany(mappedBy = "weburl", cascade = CascadeType.ALL)
    private List<Announcement> pageinfo = new ArrayList<>();

    public WebUrl(String author, String url) {
        this.author = author;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
