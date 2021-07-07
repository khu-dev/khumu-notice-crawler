package khumu.spring.batch.dto;

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

    private String name;
    private String url;
    private int pagenum;

    @OneToMany(mappedBy = "weburl", cascade = CascadeType.ALL)
    private List<WebExtractive> pageinfo = new ArrayList<>();

    public WebUrl(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
