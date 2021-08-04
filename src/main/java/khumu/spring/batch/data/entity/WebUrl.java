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
    private String fronturl;
    private String backurl;
    private int lastid;

    @OneToMany(mappedBy = "weburl", cascade = CascadeType.ALL)
    private List<Announcement> pageinfo = new ArrayList<>();

    public String getFrontUrl() {
        return fronturl;
    }
    public String getBackUrl() { return backurl; }
    public int getLastID() { return lastid; }

    public void setLastid(int lastid) {
        this.lastid = lastid;
    }
}