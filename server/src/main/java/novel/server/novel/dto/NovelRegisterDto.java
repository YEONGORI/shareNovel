package novel.server.novel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import novel.server.novel.Novel;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NovelRegisterDto {
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
                .build();
    }
}
