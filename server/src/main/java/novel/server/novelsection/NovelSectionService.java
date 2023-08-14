package novel.server.novelsection;

import novel.server.novelsection.dto.NovelSectionCreateDTO;
import org.springframework.stereotype.Service;

@Service
public interface NovelSectionService {
    NovelSection createNovelSection(Long novelId, Long memberId, NovelSectionCreateDTO createDTO);

    void voteNovelSection(Long novelSectionId, Long memberId);
}
