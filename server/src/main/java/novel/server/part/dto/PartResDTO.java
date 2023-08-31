package novel.server.part.dto;

import lombok.*;
import novel.server.novel.dto.NovelDTO;
import novel.server.part.Part;
import novel.server.like.Like;
import novel.server.writer.dto.WriterDTO;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PartResDTO {
    private Integer partNum;
    private String content;
    private List<Like> likes;
    private NovelDTO novelDto;
    private WriterDTO writerDto;

    public static PartResDTO fromEntity(Part part) {
        return PartResDTO.builder()
                .partNum(part.getPartNum())
                .content(part.getContent())
                .novelDto(NovelDTO.fromEntity(part.getNovel()))
                .writerDto(WriterDTO.fromEntity(part.getWriter()))
                .build();
    }
}
