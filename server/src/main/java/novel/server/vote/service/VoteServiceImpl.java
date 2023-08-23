package novel.server.vote.service;

import lombok.RequiredArgsConstructor;
import novel.server.member.Member;
import novel.server.member.MemberRepository;
import novel.server.member.exception.MemberNotExistsException;
import novel.server.novel.Novel;
import novel.server.novel.exception.NovelNotExistsException;
import novel.server.novelsection.NovelSectionRepository;
import novel.server.novelsection.NovelSectionService;
import novel.server.novelsection.exception.NovelSectionNotExistsException;
import novel.server.vote.VoteService;
import novel.server.writer.Writer;
import novel.server.writer.WriterRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final MemberRepository memberRepository;
    private final NovelSectionRepository novelSectionRepository;

    @Override
    public void voteForNovelSection(Long memberId, Long novelSectionId) {
        novelSectionRepository.findNovelSectionById(novelSectionId)
                .orElseThrow(() -> new NovelSectionNotExistsException("소설 섹션을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));
        Writer writer = member.getWriter();



    }
}
