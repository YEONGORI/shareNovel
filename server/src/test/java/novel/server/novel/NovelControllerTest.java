package novel.server.novel;

import jakarta.transaction.Transactional;
import novel.server.member.Member;
import novel.server.member.MemberMother;
import novel.server.member.MemberService;
import novel.server.novel.dto.NovelRegisterDto;
import novel.server.writer.Writer;
import novel.server.writer.WriterRepository;
import novel.server.writernovel.WriterNovel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class NovelControllerTest {
    @Autowired
    NovelService novelService;
    @Autowired
    MemberService memberService;
    @Autowired
    WriterRepository writerRepository;

    @Test
    @DisplayName("소설 - 작가 매핑 테스트 1")
    void writerToNovel() {
        // given
        int SIZE = 5;
        ArrayList<NovelRegisterDto> registerNovelDtos = new ArrayList<>();
        ArrayList<Novel> registeredNovels = new ArrayList<>();

        Member member = memberService.register(MemberMother.registerDto());
        Writer writer = writerRepository.findWriterByPenName(member.getPenName()).get();
        for (int i = 0; i < SIZE; i++) {
            registerNovelDtos.add(NovelMother.registerDto());
            registerNovelDtos.get(i).setPenName(member.getPenName());
        }

        // when
        for (NovelRegisterDto registerNovelDto : registerNovelDtos) {
            registeredNovels.add(novelService.register(registerNovelDto));
        }

        // then
        assertThat(writer.getWriterNovels().size()).isEqualTo(SIZE);
        for (WriterNovel wn : writer.getWriterNovels()) {
            assertThat(wn.getWriter().getPenName()).isEqualTo(writer.getPenName());
            assertThat(wn.getNovel()).isIn(registeredNovels);
        }
    }

    @Test
    @DisplayName("미등록 작가 소설 등록")
    void UnAuthroizedWriter() {
        // given
        Member member = memberService.register(MemberMother.registerDto());
        NovelRegisterDto registerDto0 = NovelMother.registerDto();
        registerDto0.setPenName("UnAuthorizedWriter");
        NovelRegisterDto registerDto1 = NovelMother.registerDto();
        registerDto1.setPenName(member.getPenName());

        // when, then
        assertThrows(BadCredentialsException.class,
                () -> novelService.register(registerDto0));

        assertDoesNotThrow(() -> novelService.register(registerDto1));
    }
}