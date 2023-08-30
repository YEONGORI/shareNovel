package novel.server.part;

import jakarta.persistence.*;
import novel.server.novel.Novel;
import novel.server.partproposal.PartProposal;

@Entity
public class Part {
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "novel_id")
    private Novel novel;

    @OneToOne
    private PartProposal selectedProposal;
}
