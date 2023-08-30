package novel.server.like;

import org.springframework.stereotype.Service;

@Service
public interface LikeService {
    void voteForNovelSection(Long memberId, Long novelSectionId);
}
