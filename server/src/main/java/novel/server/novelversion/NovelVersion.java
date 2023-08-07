package novel.server.novelversion;

import jakarta.persistence.*;
import novel.server.novel.Novel;

@Entity
public class NovelVersion {
    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private int version;
    @ManyToOne
    @JoinColumn(name = "novel_id")
    private Novel novel;
}
