package novel.server.like;

import jakarta.persistence.*;
import lombok.*;
import novel.server.part.Part;
import novel.server.writer.Writer;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "writer_id")
    private Writer writer;


    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;
}
