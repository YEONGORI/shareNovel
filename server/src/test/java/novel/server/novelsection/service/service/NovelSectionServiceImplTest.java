package novel.server.novelsection.service.service;

import novel.server.member.Member;
import novel.server.member.MemberMother;
import novel.server.member.MemberRepository;
import novel.server.member.MemberService;
import novel.server.member.dto.MemberDefaultRegisterDto;
import novel.server.novel.Novel;
import novel.server.novel.NovelMother;
import novel.server.novel.NovelRepository;
import novel.server.novel.NovelService;
import novel.server.novel.dto.NovelRegisterDto;
import novel.server.novelsection.NovelSection;
import novel.server.novelsection.NovelSectionRepository;
import novel.server.novelsection.dto.NovelSectionCreateDTO;
import novel.server.novelsection.service.NovelSectionMother;
import novel.server.vote.Vote;
import novel.server.writer.Writer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class NovelSectionServiceImplTest {
    @Autowired
    NovelService novelService;
    @Autowired
    MemberService memberService;
    @Autowired
    NovelSectionRepository novelSectionRepository;

    @Test
    @DisplayName("소설 섹션 생성 테스트")
    void createNovelSection() {
        // given
        MemberDefaultRegisterDto memberDefaultRegisterDto = MemberMother.registerDto();
        Member member = memberService.register(memberDefaultRegisterDto);
        NovelRegisterDto novelRegisterDto = NovelMother.registerDto();
        novelRegisterDto.setPenName(member.getPenName());
        Novel novel = novelService.register(novelRegisterDto);
        Writer writer = member.getWriter();
        NovelSection novelSection = NovelSectionMother.createDto().toEntity();

        // when
        novelSection.setNovel(novel);
        novelSection.setWriter(writer);
        writer.getNovelSections().add(novelSection);
        novelSectionRepository.save(novelSection);
        NovelSection section = novelSectionRepository.findNovelSectionById(novelSection.getId()).get();

        // then
        assertThat(section.getNovel().getId()).isEqualTo(novel.getId());
        assertThat(section.getWriter().getId()).isEqualTo(writer.getId());
    }

    @Test
    @DisplayName("소설 섹션 투표 테스트")
    void voteNovelSection() {
        // given
        MemberDefaultRegisterDto memberDefaultRegisterDto = MemberMother.registerDto();
        Member member = memberService.register(memberDefaultRegisterDto);

        NovelRegisterDto novelRegisterDto = NovelMother.registerDto();
        novelRegisterDto.setPenName(member.getPenName());
        Novel novel = novelService.register(novelRegisterDto);

        Writer writer = member.getWriter();
        NovelSection novelSection = NovelSectionMother.createDto().toEntity();
        novelSection.setWriter(writer);
        novelSection.setNovel(novel);
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
}