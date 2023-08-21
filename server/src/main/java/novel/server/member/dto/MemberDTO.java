package novel.server.member.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import novel.server.member.Member;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String penName;

    private String password;

    private LocalDateTime createdAt;

    public static MemberDTO fromEntity(Member member) {
        return MemberDTO.builder()
                .penName(member.getPenName())
                .password(member.getPassword())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
