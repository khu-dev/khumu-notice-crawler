package khumu.spring.batch.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "weburl")
@Entity(name = "WebUrl")
@NoArgsConstructor
@AllArgsConstructor
public class WebUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;
    private String fronturl;
    private String backurl;
    private Integer lastid;

//    @OneToMany(mappedBy = "title", cascade = CascadeType.ALL)
//    private List<Announcement> announcements = new ArrayList<>();

//    public String getFrontUrl() {
//        return fronturl;
//    }
//    public String getBackUrl() { return backurl; }
//    public String getAuthor() {return author; }
//    public Integer getLastID() { return lastid; }
//
//    public void setLastid(int lastid) {
//        this.lastid = lastid;
//    }
//
//    public void setWebUrl(String author, String fronturl, String backurl, Integer lastid) {
//        this.author = author;
//        this.fronturl = fronturl;
//        this.backurl = backurl;
//        this.lastid = lastid;
//    }
}