package novel.server.vote;

import jakarta.persistence.*;
import novel.server.novel.Novel;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String proposal;
    private String result;
    @ManyToOne
    @JoinColumn(name = "novel_id")
    private Novel novel;
}
