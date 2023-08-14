package novel.server.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import novel.server.writer.Writer;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(columnNames = "penName")})
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 30)
    @NotBlank
    private String penName;

    @Column(length = 100)
    @NotBlank
    private String password;

    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "member")
    private Writer writer;

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}
