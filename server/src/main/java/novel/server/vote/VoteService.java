package novel.server.vote;

import novel.server.novelsection.NovelSection;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface VoteService {
    void voteForNovelSection(Long memberId, Long novelSectionId);
}
