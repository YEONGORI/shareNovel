package novel.server.partproposal.service;

import novel.server.member.Member;
import novel.server.member.MemberMother;
import novel.server.member.MemberService;
import novel.server.member.dto.MemberDefaultRegisterDTO;
import novel.server.novel.Novel;
import novel.server.novel.NovelMother;
import novel.server.novel.NovelService;
import novel.server.novel.dto.NovelRegisterDTO;
import novel.server.partproposal.PartProposal;
import novel.server.partproposal.PartProposalRepository;
import novel.server.partproposal.NovelSectionMother;
import novel.server.partproposal.PartProposalService;
import novel.server.partproposal.dto.PartProposalPostReqDTO;
import novel.server.like.Like;
import novel.server.writer.Writer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class PartProposalServiceImplTest {
    @Autowired
    NovelService novelService;
    @Autowired
    MemberService memberService;
    @Autowired
    PartProposalService partProposalService;
    @Autowired
    PartProposalRepository partProposalRepository;

    private Member member;
    private Novel novel;
    private Writer writer;

    @BeforeEach
    void setup() {
        MemberDefaultRegisterDTO memberDefaultRegisterDto = MemberMother.registerDto();
        member = memberService.register(memberDefaultRegisterDto);

        NovelRegisterDTO novelRegisterDto = NovelMother.registerDto();
        novelRegisterDto.setPenName(member.getPenName());
        novel = novelService.register(novelRegisterDto);

        writer = member.getWriter();

        PartProposal partProposal = NovelSectionMother.createDto().toEntity();
        partProposal.setNovel(novel);
        partProposal.setWriter(writer);

    }

    @Test
    @DisplayName("소설 섹션 생성 테스트")
    void createNovelSection() {
        // given
        PartProposal partProposal = NovelSectionMother.createDto().toEntity();
        partProposal.setNovel(novel);
        partProposal.setWriter(writer);

        // when
        writer.getPartProposals().add(partProposal);
        partProposalRepository.save(partProposal);
        PartProposal section = partProposalRepository.findNovelSectionById(partProposal.getId()).get();

        // then
        assertThat(section.getNovel().getId()).isEqualTo(novel.getId());
        assertThat(section.getWriter().getId()).isEqualTo(writer.getId());
    }

    @Test
    @DisplayName("소설 섹션 투표 서비스 테스트1")
    void voteNovelSection1() {
        // given
        PartProposal partProposal = NovelSectionMother.createDto().toEntity();
        partProposal.setNovel(novel);
        partProposal.setWriter(writer);

        writer.getPartProposals().add(partProposal);
        partProposalRepository.save(partProposal);


        Like like = Like.builder()
                .writer(writer)
                .partProposal(partProposal)
                .votedAt(LocalDateTime.now())
                .build();

        // when
        partProposal.getVotes().add(like);
        PartProposal section = partProposalRepository.findNovelSectionById(partProposal.getId()).get();

        // then
        assertThat(section.getVotes().size()).isEqualTo(1);
        partProposal.getVotes().add(like);
        assertThat(section.getVotes().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("소설 섹션 투표 서비스 테스트2")
    protected void voteNovelSection2() {
        // given
        PartProposalPostReqDTO partProposalPostReqDTO = NovelSectionMother.createDto();
        partProposalService.createNovelSection(novel.getId(), member.getId(), partProposalPostReqDTO);
        List<PartProposal> partProposals = partProposalRepository.findNovelSectionsByNovel(novel).get();
        PartProposal partProposal = partProposals.get(0);

        // when
        partProposalService.voteNovelSection(partProposal.getId(), member.getId());
        PartProposal section = partProposalRepository.findNovelSectionById(partProposal.getId()).get();

        // then
        Assertions.assertThat(section.getVotes().size()).isEqualTo(1);
    }
}