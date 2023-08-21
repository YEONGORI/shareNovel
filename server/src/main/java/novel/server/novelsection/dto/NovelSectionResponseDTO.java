package novel.server.novelsection.dto;

import lombok.*;
import novel.server.novel.Novel;
import novel.server.novel.dto.NovelDTO;
import novel.server.novelsection.NovelSection;
import novel.server.vote.Vote;
import novel.server.writer.Writer;
import novel.server.writer.dto.WriterDTO;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NovelSectionResponseDTO {
    private String content;
    private Integer part;
    private List<Vote> votes;
    private NovelDTO novelDto;
    private WriterDTO writer;

    public static NovelSectionResponseDTO fromEntity(NovelSection novelSection) {
        return NovelSectionResponseDTO.builder()
                .content(novelSection.getContent())
                .part(novelSection.getPart())
                .votes(novelSection.getVotes())
                .novelDto(NovelDTO.fromEntity(novelSection.getNovel()))
                .writer(WriterDTO.fromEntity(novelSection.getWriter()))
                .build();
    }
}
