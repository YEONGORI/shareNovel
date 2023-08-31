package novel.server.like;

import org.springframework.stereotype.Service;

@Service
public interface LikeService {
    void likeForPart(Long memberId, Long partId);

    void cancelLikeForPart(Long memberId, Long partId);
}
