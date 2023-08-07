package novel.server.novelsection;

import jakarta.persistence.*;
import novel.server.novel.Novel;

@Entity
public class NovelSection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "novel_id", nullable = false)
    private Novel novel;
}
