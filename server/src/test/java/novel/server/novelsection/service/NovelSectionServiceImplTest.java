package novel.server.novelsection.service;

import novel.server.member.Member;
import novel.server.member.MemberMother;
import novel.server.member.MemberService;
import novel.server.member.dto.MemberDefaultRegisterDTO;
import novel.server.novel.Novel;
import novel.server.novel.NovelMother;
import novel.server.novel.NovelService;
import novel.server.novel.dto.NovelRegisterDTO;
import novel.server.novelsection.NovelSection;
import novel.server.novelsection.NovelSectionRepository;
import novel.server.novelsection.NovelSectionMother;
import novel.server.novelsection.NovelSectionService;
import novel.server.novelsection.dto.NovelSectionCreateDTO;
import novel.server.vote.Vote;
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
class NovelSectionServiceImplTest {
    @Autowired
    NovelService novelService;
    @Autowired
    MemberService memberService;
    @Autowired
    NovelSectionService novelSectionService;
    @Autowired
    NovelSectionRepository novelSectionRepository;

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

        NovelSection novelSection = NovelSectionMother.createDto().toEntity();
        novelSection.setNovel(novel);
        novelSection.setWriter(writer);

    }

    @Test
    @DisplayName("소설 섹션 생성 테스트")
    void createNovelSection() {
        // given
        NovelSection novelSection = NovelSectionMother.createDto().toEntity();
        novelSection.setNovel(novel);
        novelSection.setWriter(writer);

        // when
        writer.getNovelSections().add(novelSection);
        novelSectionRepository.save(novelSection);
        NovelSection section = novelSectionRepository.findNovelSectionById(novelSection.getId()).get();

        // then
        assertThat(section.getNovel().getId()).isEqualTo(novel.getId());
        assertThat(section.getWriter().getId()).isEqualTo(writer.getId());
    }

    @Test
    @DisplayName("소설 섹션 투표 서비스 테스트1")
    void voteNovelSection1() {
        // given
        NovelSection novelSection = NovelSectionMother.createDto().toEntity();
        novelSection.setNovel(novel);
        novelSection.setWriter(writer);

        writer.getNovelSections().add(novelSection);
        novelSectionRepository.save(novelSection);


        Vote vote = Vote.builder()
                .writer(writer)
                .novelSection(novelSection)
                .votedAt(LocalDateTime.now())
                .build();

        // when
        novelSection.getVotes().add(vote);
        NovelSection section = novelSectionRepository.findNovelSectionById(novelSection.getId()).get();

        // then
        assertThat(section.getVotes().size()).isEqualTo(1);
        novelSection.getVotes().add(vote);
        assertThat(section.getVotes().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("소설 섹션 투표 서비스 테스트2")
    protected void voteNovelSection2() {
        // given
        NovelSectionCreateDTO novelSectionCreateDTO = NovelSectionMother.createDto();
        novelSectionService.createNovelSection(novel.getId(), member.getId(), novelSectionCreateDTO);
        List<NovelSection> novelSections = novelSectionRepository.findNovelSectionByNovel(novel).get();
        NovelSection novelSection = novelSections.get(0);

        // when
        novelSectionService.voteNovelSection(novelSection.getId(), member.getId());
        NovelSection section = novelSectionRepository.findNovelSectionById(novelSection.getId()).get();

        // then
        Assertions.assertThat(section.getVotes().size()).isEqualTo(1);
    }
}