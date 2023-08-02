package novel.server.domain.writer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "writer", uniqueConstraints = {@UniqueConstraint(columnNames = "penName")})
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 30)
    @NotEmpty
    private String penName;
    @Column(length = 100)
    @NotEmpty
    private String password;
    private LocalDateTime createdAt;

    public Writer(String penName, String password) {
        this.penName = penName;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
}
