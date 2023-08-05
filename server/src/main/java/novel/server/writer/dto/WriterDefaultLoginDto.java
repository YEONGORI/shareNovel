package novel.server.writer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class WriterDefaultLoginDto {
    @NotBlank
    @Length(max = 30)
    private String penName;

    @NotBlank
    @Length(max = 100)
    private String password;
}
