package novel.server.novel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import novel.server.novel.Novel;
import novel.server.novel.NovelStatus;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NovelRegisterDTO {
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
    @Setter
    @NotBlank
    @Length(max = 30)
    private String penName;

    public Novel toEntity() {
        return Novel.builder()
                .title(title)
                .plot(plot)
                .theme(theme)
                .characters(characters)
                .background(background)
                .event(event)
                .status(NovelStatus.ONGOING)
                .partProposals(new ArrayList<>())
                .writerNovels(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
