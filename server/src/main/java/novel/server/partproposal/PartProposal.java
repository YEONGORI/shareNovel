package novel.server.partproposal;

import jakarta.persistence.*;
import lombok.*;
import novel.server.like.Like;
import novel.server.novel.Novel;
import novel.server.part.Part;
import novel.server.writer.Writer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "part_id", nullable = false)
    private Part part;

    @Setter
    @ManyToOne
    @JoinColumn(name = "novel_id", nullable = false)
    private Novel novel;

    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Writer writer;

    @OneToMany(mappedBy = "partProposal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();
}
