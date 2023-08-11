package novel.server.writer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import novel.server.writernovel.WriterNovel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
    @Column(length = 100)
    @NotBlank
    private String password;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "writer")
    private List<WriterNovel> writerNovels = new ArrayList<>();

//    public Writer(String penName, String password) {
//        this.penName = penName;
//        this.password = password;
//        this.createdAt = LocalDateTime.now();
//    }
}
