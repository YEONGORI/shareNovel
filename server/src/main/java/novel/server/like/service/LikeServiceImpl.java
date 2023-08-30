package novel.server.like.service;

import lombok.RequiredArgsConstructor;
import novel.server.member.Member;
import novel.server.member.MemberRepository;
import novel.server.member.exception.MemberNotExistsException;
import novel.server.partproposal.PartProposalRepository;
import novel.server.partproposal.exception.PartProposalNotExistsException;
import novel.server.like.LikeService;
import novel.server.writer.Writer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final MemberRepository memberRepository;
    private final PartProposalRepository partProposalRepository;

    @Override
    public void voteForNovelSection(Long memberId, Long novelSectionId) {
        partProposalRepository.findNovelSectionById(novelSectionId)
                .orElseThrow(() -> new PartProposalNotExistsException("소설 섹션을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));
        Writer writer = member.getWriter();



    }
}
