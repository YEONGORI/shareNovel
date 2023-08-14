package novel.server.novelsection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import novel.server.novelsection.NovelSection;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NovelSectionCreateDTO {
    @NotBlank
    @Length(max = 5000)
    private String content;

    @NotBlank
    @Size(max = 5000)
    private Integer part;

    public NovelSection toEntity() {
        return NovelSection.builder()
                .content(content)
                .part(part)
                .build();
    }
}
