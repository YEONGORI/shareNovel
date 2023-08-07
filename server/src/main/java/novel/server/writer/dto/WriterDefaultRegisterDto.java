package novel.server.writer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import novel.server.novel.Novel;
import novel.server.writer.Writer;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WriterDefaultRegisterDto {
    @NotBlank
    @Length(max = 30)
    private String penName;

    @NotBlank
    @Length(max = 100)
    private String password;

    public Writer toEntity() {
        return Writer.builder()
                .penName(penName)
                .password(password)
                .build();

    }
}
