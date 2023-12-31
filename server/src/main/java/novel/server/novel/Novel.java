package novel.server.novel;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import novel.server.part.Part;
import novel.server.stake.Stake;
import novel.server.writernovel.WriterNovel;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Novel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 100)
    private String title;

    @NotBlank
    @Length(max = 500)
    private String plot;

    @NotBlank
    @Length(max = 500)
    private String theme;

    @NotBlank
    @Length(max = 100)
    private String characters;

    @NotBlank
    @Length(max = 100)
    private String background;

    @NotBlank
    @Length(max = 200)
    private String event;

    private NovelStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "novel")
    private List<WriterNovel> writerNovels = new ArrayList<>();

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Part> parts = new ArrayList<>();

    @OneToMany(mappedBy = "novel")
    private List<Stake> stakes = new ArrayList<>();
}