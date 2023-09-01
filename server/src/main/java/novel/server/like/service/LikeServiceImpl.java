package novel.server.like.service;

import lombok.RequiredArgsConstructor;
import novel.server.like.Like;
import novel.server.like.LikeRepository;
import novel.server.like.exception.AlreadyCanceledException;
import novel.server.like.exception.AlreadyLikedException;
import novel.server.member.Member;
import novel.server.member.MemberRepository;
import novel.server.member.exception.MemberNotExistsException;
import novel.server.part.Part;
import novel.server.part.PartRepository;
import novel.server.part.exception.PartNotExistsException;
import novel.server.like.LikeService;
import novel.server.writer.Writer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final MemberRepository memberRepository;
    private final PartRepository partRepository;
    private final LikeRepository likeRepository;

    @Override
    public void likeForPart(Long memberId, Long partId) {
        Part part = partRepository.findPartById(partId)
                .orElseThrow(() -> new PartNotExistsException("파트를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));
        Writer writer = member.getWriter();
        if (part.getLikes().stream().anyMatch(like -> like.getWriter().equals(writer))) {
            throw new AlreadyLikedException("이미 좋아요를 누르셨습니다.");
        }


        Like like = Like.builder()
                .part(part)
                .writer(writer)
                .build();

        part.getLikes().add(like);
    }

    @Override
    public void cancelLikeForPart(Long memberId, Long partId) {
        Part part = partRepository.findPartById(partId)
                .orElseThrow(() -> new PartNotExistsException("파트를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));
        Writer writer = member.getWriter();

        Like like = part.getLikes().stream().filter(l -> l.getWriter().equals(writer)).findAny()
                .orElseThrow(() -> new AlreadyCanceledException("이미 취소하셨습니다."));

        part.getLikes().remove(like);
        likeRepository.delete(like);
    }
}
