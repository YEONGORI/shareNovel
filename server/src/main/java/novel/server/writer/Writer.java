package novel.server.writer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import novel.server.member.Member;
import novel.server.part.Part;
import novel.server.stake.Stake;
import novel.server.writernovel.WriterNovel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "writer", uniqueConstraints = {@UniqueConstraint(columnNames = "penName")})
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 30)
    @NotBlank
    private String penName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "writer")
    private List<WriterNovel> writerNovels = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Part> parts = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Stake> stakes = new ArrayList<>();
}
