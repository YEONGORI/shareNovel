package novel.server.stake;

import jakarta.persistence.*;
import lombok.*;
import novel.server.novel.Novel;
import novel.server.writer.Writer;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private double stakeValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Writer writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "novel_id", nullable = false)
    private Novel novel;
}
