package novel.server.novelsection.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import novel.server.member.Member;
import novel.server.member.MemberRepository;
import novel.server.member.exception.MemberNotExistsException;
import novel.server.novel.Novel;
import novel.server.novel.NovelRepository;
import novel.server.novel.exception.NovelNotExistsException;
import novel.server.novelsection.NovelSection;
import novel.server.novelsection.NovelSectionRepository;
import novel.server.novelsection.NovelSectionService;
import novel.server.novelsection.dto.NovelSectionCreateDTO;
import novel.server.novelsection.dto.NovelSectionResponseDTO;
import novel.server.novelsection.exception.DuplicatedVoteException;
import novel.server.novelsection.exception.NovelSectionNotExistsException;
import novel.server.vote.Vote;
import novel.server.writer.Writer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class NovelSectionServiceImpl implements NovelSectionService {
    private final NovelSectionRepository novelSectionRepository;
    private final NovelRepository novelRepository;
    private final MemberRepository memberRepository;

    @Override
    public NovelSectionResponseDTO createNovelSection(Long novelId, Long memberId, NovelSectionCreateDTO createDTO) {
        Novel novel = novelRepository.findNovelById(novelId)
                .orElseThrow(() -> new NovelNotExistsException("소설을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));

        Writer writer = member.getWriter();

        NovelSection novelSection = createDTO.toEntity();
        novelSection.setNovel(novel);
        novelSection.setWriter(writer);
        writer.getNovelSections().add(novelSection);

        NovelSection savedNovelSection = novelSectionRepository.save(novelSection);
        return NovelSectionResponseDTO.fromEntity(savedNovelSection);
    }

    @Override
    public void voteNovelSection(Long novelSectionId, Long memberId) {
        NovelSection novelSection = novelSectionRepository.findNovelSectionById(novelSectionId)
                .orElseThrow(() -> new NovelSectionNotExistsException("투표 하는 소설 구역을 확인해주세요."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));
        if (novelSection.getVotes().stream().anyMatch(vote -> vote.getWriter().getId() == memberId)) {
            throw new DuplicatedVoteException("중복 투표입니다.");
        }

        Writer writer = member.getWriter();

        Vote vote = Vote.builder()
                .writer(writer)
                .novelSection(novelSection)
                .votedAt(LocalDateTime.now())
                .build();

        novelSection.getVotes().add(vote);
    }
}
