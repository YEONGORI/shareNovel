package novel.server.like;

import com.fasterxml.jackson.databind.ObjectMapper;
import novel.server.member.Member;
import novel.server.member.MemberMother;
import novel.server.member.MemberService;
import novel.server.member.auth.TokenInfo;
import novel.server.member.dto.MemberDefaultLoginDTO;
import novel.server.novel.Novel;
import novel.server.novel.NovelMother;
import novel.server.novel.NovelService;
import novel.server.novel.dto.NovelRegisterDTO;
import novel.server.part.Part;
import novel.server.part.PartMother;
import novel.server.part.PartRepository;
import novel.server.part.PartService;
import novel.server.writer.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class LikeControllerTest {
    @Autowired
    MemberService memberService;

    @Autowired
    NovelService novelService;

    @Autowired
    PartService partService;

    @Autowired
    PartRepository partRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    private Member member;
    private Writer writer;
    private Novel novel;
    private TokenInfo tokenInfo;
    private Part part;

    @BeforeEach
    void setup() {
        member = memberService.register(MemberMother.registerDto());
        writer = member.getWriter();

        NovelRegisterDTO novelRegisterDTO = NovelMother.registerDto();
        novelRegisterDTO.setPenName(member.getPenName());
        novel = novelService.register(novelRegisterDTO);

        MemberDefaultLoginDTO loginDTO = MemberDefaultLoginDTO.builder()
                .penName(member.getPenName())
                .password(member.getPassword())
                .build();
        tokenInfo = memberService.login(loginDTO);

        part = Part.builder()
                .partNum(1)
                .content(PartMother.createDto().getContent())
                .likes(new ArrayList<>())
                .novel(novel)
                .writer(writer)
                .build();

        part = partRepository.save(part);
    }

    @Test
    @DisplayName("좋아요 컨트롤러 테스트")
    void likeForPart() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/like/{partId}", part.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        // then
        resultActions.andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("좋아요 중복 컨트롤러 테스트")
    void duplicateLikeForPart() throws Exception {
        // given
        Part newPart = Part.builder()
                .partNum(2)
                .content(PartMother.createDto().getContent())
                .likes(new ArrayList<>())
                .novel(novel)
                .writer(writer)
                .build();

        newPart = partRepository.save(newPart);

        // when
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/like/{partId}", part.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        ResultActions resultActions1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/like/{partId}", part.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        ResultActions resultActions2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/like/{partId}", newPart.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        // then
        resultActions1.andExpect(status().isGone());
        resultActions2.andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("좋아요 취소 컨트롤러 테스트")
    void cancelLikeForPart() throws Exception {
        // given
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/like/{partId}", part.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/like/{partId}", part.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        // then
        resultActions.andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("좋아요 중복 취소 컨트롤러 테스트")
    void duplicateCancelLikeForPart() throws Exception {
        // given
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/like/{partId}", part.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        // when
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/like/{partId}", part.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/like/{partId}", part.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        // then
        resultActions.andExpect(status().isGone());
    }
}