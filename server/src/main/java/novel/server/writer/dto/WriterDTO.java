package novel.server.writer.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import novel.server.member.Member;
import novel.server.member.dto.MemberDTO;
import novel.server.writer.Writer;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WriterDTO {
    private String penName;

    private MemberDTO memberDto;

    public static WriterDTO fromEntity(Writer writer) {
        return WriterDTO.builder()
                .penName(writer.getPenName())
                .memberDto(MemberDTO.fromEntity(writer.getMember()))
                .build();
    }
}
