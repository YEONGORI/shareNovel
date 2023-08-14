package novel.server.novel.service;

import novel.server.member.dto.MemberDefaultRegisterDto;
import novel.server.novel.Novel;
import novel.server.novel.NovelMother;
import novel.server.novel.NovelRepository;
import novel.server.novel.dto.NovelRegisterDto;
import novel.server.member.Member;
import novel.server.member.MemberMother;
import novel.server.member.MemberRepository;
import novel.server.writer.Writer;
import novel.server.writer.WriterRepository;
import novel.server.writernovel.WriterNovel;
import novel.server.writernovel.WriterNovelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
class NovelServiceImplTest {
    @Autowired
    NovelRepository novelRepository;
    @Autowired
    WriterRepository writerRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    WriterNovelRepository writerNovelReposyitory;

    @Test
    @DisplayName("소설 등록")
    void register() {
        // given
        MemberDefaultRegisterDto memberRegisterDto = MemberMother.registerDto();
        Member member = memberRegisterDto.toEntity();
        Member savedMember = memberRepository.save(member);

        NovelRegisterDto registerDto = NovelMother.registerDto();
        Novel novel = registerDto.toEntity();
        novelRepository.save(novel);

        Writer writer = createWriter(member);
        savedMember.setWriter(writer);
        writerRepository.save(writer);

        // when
        WriterNovel writerNovel = WriterNovel.builder()
                .writer(writer)
                .novel(novel)
                .build();
        writerNovelReposyitory.save(writerNovel);

        // then
        Optional<WriterNovel> byNovel = writerNovelReposyitory.findByNovel(novel);
        Optional<WriterNovel> byMember = writerNovelReposyitory.findByWriter(writer);
        assertThat(byNovel.get()).isEqualTo(byMember.get());
    }

    private static Writer createWriter(Member member) {
        return Writer.builder()
                .penName(member.getPenName())
                .member(member)
                .build();
    }

    @Test
    @DisplayName("소설 - 작가 매핑 테스트")
    void register_mapping() {
        // given
        MemberDefaultRegisterDto memberRegisterDto = MemberMother.registerDto();
        Member member = memberRepository.save(memberRegisterDto.toEntity());

        NovelRegisterDto registerDto0 = NovelMother.registerDto();
        NovelRegisterDto registerDto1 = NovelMother.registerDto();
        Novel novel = novelRepository.save(registerDto0.toEntity());
        novelRepository.save(registerDto1.toEntity());

        Writer writer = createWriter(member);
        member.setWriter(writer);
        writerRepository.save(writer);

        // when
        WriterNovel writerNovel = WriterNovel.builder()
                .writer(writer)
                .novel(novel)
                .build();
        writerNovelReposyitory.save(writerNovel);

        // then
        WriterNovel byNovel = writerNovelReposyitory.findByNovel(novel).get();
        WriterNovel byMember = writerNovelReposyitory.findByWriter(writer).get();
        assertThat(byNovel.getNovel().getId()).isEqualTo(novel.getId());
        assertThat(byMember.getWriter().getId()).isEqualTo(writer.getId());
    }
}