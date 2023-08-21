package novel.server.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import novel.server.member.Member;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDefaultRegisterDTO {
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
                .createdAt(LocalDateTime.now())
                .build();

    }
}
