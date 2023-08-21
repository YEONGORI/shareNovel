package novel.server.novelsection;

import novel.server.novelsection.dto.NovelSectionCreateDTO;
import novel.server.novelsection.dto.NovelSectionResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface NovelSectionService {
    NovelSectionResponseDTO createNovelSection(Long novelId, Long memberId, NovelSectionCreateDTO createDTO);

    void voteNovelSection(Long novelSectionId, Long memberId);
}
