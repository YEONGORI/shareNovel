package novel.server.part.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import novel.server.part2.Part2;
import novel.server.part.Part;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PartCreateReqDTO {

    @NotBlank
    @Length(max = 5000)
    private String content;

    public Part toEntity() {
        return Part.builder()
                .content(content)
                .build();
    }
}
