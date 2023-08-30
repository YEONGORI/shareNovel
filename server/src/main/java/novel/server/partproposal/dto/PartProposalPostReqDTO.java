package novel.server.partproposal.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import novel.server.part.Part;
import novel.server.partproposal.PartProposal;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PartProposalPostReqDTO {
    @NotBlank
    @Length(max = 5000)
    private String content;

    @NotNull
    private Part part;

    public PartProposal toEntity() {
        return PartProposal.builder()
                .content(content)
                .part(part)
                .build();
    }
}
