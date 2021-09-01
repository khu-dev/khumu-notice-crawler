package khumu.spring.batch.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "board")
@Entity(name = "Board")
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "fk_author_board"))
    private Author author;

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