package novel.server.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import novel.server.member.Member;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDefaultRegisterDto {
    @NotBlank
    @Length(max = 30)
    private String penName;

    @NotBlank
    @Length(max = 100)
    private String password;

    public Member toEntity() {
        return Member.builder()
                .penName(penName)
                .password(password)
                .build();

    }
}
