package novel.server.like;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import novel.server.partproposal.PartProposal;
import novel.server.writer.Writer;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "writer_id")
    private Writer writer;

    @ManyToOne
    @JoinColumn(name = "part_proposal_id")
    private PartProposal partProposal;
}
