package novel.server.like.service;

import novel.server.like.Like;
import novel.server.like.LikeRepository;
import novel.server.like.LikeService;
import novel.server.member.Member;
import novel.server.member.MemberMother;
import novel.server.member.MemberService;
import novel.server.member.exception.MemberNotExistsException;
import novel.server.novel.Novel;
import novel.server.novel.NovelMother;
import novel.server.novel.NovelService;
import novel.server.novel.dto.NovelRegisterDTO;
import novel.server.part.Part;
import novel.server.part.PartMother;
import novel.server.part.PartRepository;
import novel.server.part.exception.PartNotExistsException;
import novel.server.writer.Writer;
import novel.server.writer.WriterRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class LikeServiceImplTest {
    @Autowired
    MemberService memberService;
    @Autowired
    NovelService novelService;
    @Autowired
    LikeService likeService;
    @Autowired
    PartRepository partRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    WriterRepository writerRepository;

    private Member member;
    private Writer writer;
    private Novel novel;
    private Part part;

    @BeforeEach
    void setup() {
        member = memberService.register(MemberMother.registerDto());
        writer = member.getWriter();

        NovelRegisterDTO novelRegisterDTO = NovelMother.registerDto();
        novelRegisterDTO.setPenName(member.getPenName());
        novel = novelService.register(novelRegisterDTO);

        part = Part.builder()
                .partNum(1)
                .content(PartMother.createDto().getContent())
                .likes(new ArrayList<>())
                .novel(novel).writer(writer)
                .build();

        part = partRepository.save(part);
    }


    @Test
    @DisplayName("좋아요 서비스 테스트")
    void likeForPart() {
        // given
        likeService.likeForPart(member.getId(), part.getId());

        // when
        boolean isSuccessByPart = likeRepository.findAll().stream().anyMatch(like -> like.getPart().equals(part));
        boolean isSuccessByWriter = likeRepository.findAll().stream().anyMatch(like -> like.getWriter().equals(member.getWriter()));

        // then
        assertThat(isSuccessByPart).isTrue();
        assertThat(isSuccessByWriter).isTrue();
    }

    @Test
    void cancelLikeForPart() {
        // given
        likeService.likeForPart(member.getId(), part.getId());

        // when
        likeService.cancelLikeForPart(member.getId(), part.getId());

        // then
        assertThat(likeRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("좋아요 서비스 인자 오류 테스트")
    void likeWrongArguTest() {
        assertThrows(MemberNotExistsException.class,
                () -> likeService.likeForPart(member.getId() + 1, part.getId()));

        assertThrows(PartNotExistsException.class,
                () -> likeService.likeForPart(member.getId(), part.getId() + 1));
    }

    @Test
    @DisplayName("좋아요 취소 인자 오류 테스트")
    void cancelWrongArguTest() {
        // given
        assertThrows(MemberNotExistsException.class,
                () -> likeService.cancelLikeForPart(member.getId() + 1, part.getId()));

        assertThrows(PartNotExistsException.class,
                () -> likeService.cancelLikeForPart(member.getId(), part.getId() + 1));
    }
}