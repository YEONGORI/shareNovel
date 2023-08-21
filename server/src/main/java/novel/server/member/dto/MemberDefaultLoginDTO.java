package novel.server.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDefaultLoginDTO {
    @NotBlank
    @Length(max = 30)
    private String penName;

    @NotBlank
    @Length(max = 100)
    private String password;
}
