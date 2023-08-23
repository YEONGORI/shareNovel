package novel.server.novelsection;

import jakarta.persistence.*;
import lombok.*;
import novel.server.novel.Novel;
import novel.server.vote.Vote;
import novel.server.writer.Writer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NovelSection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Integer part;
//    @OneToMany(mappedBy = "novelSection", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Vote> votes = new ArrayList<>();
    @Setter
    @ManyToOne
    @JoinColumn(name = "novel_id", nullable = false)
    private Novel novel;
    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Writer writer;
}
