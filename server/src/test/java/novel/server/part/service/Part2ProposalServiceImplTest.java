package novel.server.part.service;

import novel.server.member.Member;
import novel.server.member.MemberMother;
import novel.server.member.MemberService;
import novel.server.member.dto.MemberDefaultRegisterDTO;
import novel.server.novel.Novel;
import novel.server.novel.NovelMother;
import novel.server.novel.NovelService;
import novel.server.novel.dto.NovelRegisterDTO;
import novel.server.part.Part;
import novel.server.part.PartRepository;
import novel.server.part.NovelSectionMother;
import novel.server.part.PartService;
import novel.server.part.dto.PartCreateReqDTO;
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
class PartServiceImplTest {
    @Autowired
    NovelService novelService;
    @Autowired
    MemberService memberService;
    @Autowired
    PartService partService;
    @Autowired
    PartRepository partRepository;

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

        Part part = NovelSectionMother.createDto().toEntity();
        part.setNovel(novel);
        part.setWriter(writer);

    }

    @Test
    @DisplayName("소설 섹션 생성 테스트")
    void createNovelSection() {
        // given
        Part part = NovelSectionMother.createDto().toEntity();
        part.setNovel(novel);
        part.setWriter(writer);

        // when
        writer.getParts().add(part);
        partRepository.save(part);
        Part section = partRepository.findPartById(part.getId()).get();

        // then
        assertThat(section.getNovel().getId()).isEqualTo(novel.getId());
        assertThat(section.getWriter().getId()).isEqualTo(writer.getId());
    }

    @Test
    @DisplayName("소설 섹션 투표 서비스 테스트1")
    void voteNovelSection1() {
        // given
        Part part = NovelSectionMother.createDto().toEntity();
        part.setNovel(novel);
        part.setWriter(writer);

        writer.getParts().add(part);
        partRepository.save(part);


        Like like = Like.builder()
                .writer(writer)
                .part(part)
                .votedAt(LocalDateTime.now())
                .build();

        // when
        part.getVotes().add(like);
        Part section = partRepository.findPartById(part.getId()).get();

        // then
        assertThat(section.getVotes().size()).isEqualTo(1);
        part.getVotes().add(like);
        assertThat(section.getVotes().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("소설 섹션 투표 서비스 테스트2")
    protected void voteNovelSection2() {
        // given
        PartCreateReqDTO partCreateReqDTO = NovelSectionMother.createDto();
        partService.createNovelSection(novel.getId(), member.getId(), partCreateReqDTO);
        List<Part> parts = partRepository.findPartsByNovel(novel).get();
        Part part = parts.get(0);

        // when
        partService.voteNovelSection(part.getId(), member.getId());
        Part section = partRepository.findPartById(part.getId()).get();

        // then
        Assertions.assertThat(section.getVotes().size()).isEqualTo(1);
    }
}