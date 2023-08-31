package novel.server.part;

import jakarta.persistence.*;
import lombok.*;
import novel.server.like.Like;
import novel.server.novel.Novel;
import novel.server.writer.Writer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer partNum;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Setter
    @ManyToOne
    @JoinColumn(name = "novel_id", nullable = false)
    private Novel novel;

    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Writer writer;

    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public void updatePartContent(String content) {
        this.content = content;
    }
}
