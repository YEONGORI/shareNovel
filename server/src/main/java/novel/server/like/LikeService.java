package novel.server.like;

import org.springframework.stereotype.Service;

@Service
public interface LikeService {
    void likeForPartProposal(Long memberId, Long novelSectionId);
}
