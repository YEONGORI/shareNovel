package novel.server.part2;

import jakarta.persistence.*;
import novel.server.novel.Novel;
import novel.server.part.Part;

@Entity
public class Part2 {
    @Id
    @GeneratedValue
    private Long id;

    private Integer partNum;

    private String content;

    @ManyToOne
    @JoinColumn(name = "novel_id")
    private Novel novel;

    @OneToOne
    private Part selectedProposal;
}
