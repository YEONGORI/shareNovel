package novel.server.novelsection.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import novel.server.member.Member;
import novel.server.member.MemberMother;
import novel.server.member.MemberService;
import novel.server.member.auth.TokenInfo;
import novel.server.member.dto.MemberDefaultLoginDto;
import novel.server.novel.Novel;
import novel.server.novel.NovelMother;
import novel.server.novel.NovelService;
import novel.server.novel.dto.NovelRegisterDto;
import novel.server.novelsection.NovelSectionService;
import novel.server.novelsection.dto.NovelSectionCreateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class NovelSectionControllerTest {
    @Autowired
    NovelSectionService novelSectionService;
    @Autowired
    MemberService memberService;
    @Autowired
    NovelService novelService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    public static Member member;
    public static Novel novel;

    @BeforeEach
    void setup() {
        member = memberService.register(MemberMother.registerDto());
        NovelRegisterDto novelRegisterDto = NovelMother.registerDto();
        novelRegisterDto.setPenName(member.getPenName());
        novel = novelService.register(novelRegisterDto);
    }

    @Test
    @DisplayName("소설 섹션 생성 컨트롤러 테스트")
    void createNovelSection() throws Exception {
        // given
        String novelId = "1";
        MemberDefaultLoginDto loginDto = MemberDefaultLoginDto.builder().penName(member.getPenName()).password(member.getPassword()).build();
        TokenInfo tokenInfo = memberService.login(loginDto);
        NovelSectionCreateDTO novelSectionCreateDTO = NovelSectionMother.createDto();

        System.out.println("tokenInfo = " + tokenInfo.getAccessToken());
        System.out.println("tokenInfo = " + tokenInfo.getGrantType());
        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/section/{novelId}", novelId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novelSectionCreateDTO))
                .header("Authorization", tokenInfo.getGrantType(), tokenInfo.getAccessToken())
        );

        // then
        resultActions.andExpect(status().isCreated());
    }
}
