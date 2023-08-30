package novel.server.novel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import novel.server.novel.Novel;
import novel.server.novel.NovelStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NovelDTO {
    private String title;
    private String plot;
    private String theme;
    private String characters;
    private String background;
    private String event;
    private NovelStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NovelDTO fromEntity(Novel novel) {
        return NovelDTO.builder()
                .title(novel.getTitle())
                .plot(novel.getPlot())
                .theme(novel.getTheme())
                .characters(novel.getCharacters())
                .background(novel.getBackground())
                .event(novel.getEvent())
                .status(novel.getStatus())
                .createdAt(novel.getCreatedAt())
                .updatedAt(novel.getUpdatedAt())
                .build();
    }
}
