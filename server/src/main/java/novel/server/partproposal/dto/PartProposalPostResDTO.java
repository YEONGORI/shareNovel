package novel.server.partproposal.dto;

import lombok.*;
import novel.server.novel.dto.NovelDTO;
import novel.server.part.Part;
import novel.server.partproposal.PartProposal;
import novel.server.like.Like;
import novel.server.writer.dto.WriterDTO;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PartProposalPostResDTO {
    private String content;
    private Part part;
    private List<Like> likes;
    private NovelDTO novelDto;
    private WriterDTO writer;

    public static PartProposalPostResDTO fromEntity(PartProposal partProposal) {
        return PartProposalPostResDTO.builder()
                .content(partProposal.getContent())
                .part(partProposal.getPart())
                .novelDto(NovelDTO.fromEntity(partProposal.getNovel()))
                .writer(WriterDTO.fromEntity(partProposal.getWriter()))
                .build();
    }
}
