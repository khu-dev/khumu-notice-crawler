package khumu.spring.batch.data.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Follow")
@Table(name = "follow")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "follower")
    private User follower;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "followauthor")
    private Author followAuthor;
}
