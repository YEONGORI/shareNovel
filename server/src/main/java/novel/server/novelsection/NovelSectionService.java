package novel.server.novelsection;

import novel.server.novelsection.dto.NovelSectionCreateDTO;
import novel.server.novelsection.dto.NovelSectionResponseDTO;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;

@Service
public interface NovelSectionService {
    NovelSectionResponseDTO createNovelSection(Long novelId, Long memberId, NovelSectionCreateDTO createDTO);

    Map<Integer, List<NovelSection>> getNovelSectionList(Long novelId);
}
