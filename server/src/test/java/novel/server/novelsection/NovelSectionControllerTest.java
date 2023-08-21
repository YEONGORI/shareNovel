package novel.server.novelsection;

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
import novel.server.novelsection.dto.NovelSectionCreateDTO;
import org.assertj.core.api.Assertions;
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

import java.util.List;

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
    NovelSectionRepository novelSectionRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    private Member member;
    private Novel novel;
    private TokenInfo tokenInfo;

    @BeforeEach
    void setup() {
        member = memberService.register(MemberMother.registerDto());

        NovelRegisterDTO novelRegisterDto = NovelMother.registerDto();
        novelRegisterDto.setPenName(member.getPenName());
        novel = novelService.register(novelRegisterDto);

        MemberDefaultLoginDTO loginDto = MemberDefaultLoginDTO.builder()
                .penName(member.getPenName()).password(member.getPassword()).build();
        tokenInfo = memberService.login(loginDto);
    }

    @Test
    @DisplayName("소설 섹션 생성 컨트롤러 테스트")
    void createNovelSection() throws Exception {
        // given
        NovelSectionCreateDTO novelSectionCreateDTO = NovelSectionMother.createDto();

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/section/{novelId}", novel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novelSectionCreateDTO))
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("소설 섹션 생성 요청 미인증 사용자 테스트")
    void confirmNovelSectionUser() throws Exception {
        // given
        NovelSectionCreateDTO novelSectionCreateDTO = NovelSectionMother.createDto();

        // when
        ResultActions resultAction1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/section/{novelId}", novel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novelSectionCreateDTO))
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken() + "0")
        );
        ResultActions resultAction2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/section/{novelId}", novel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novelSectionCreateDTO))
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        // then
        resultAction1.andExpect(status().isUnauthorized());
        resultAction2.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("소설 섹션 생성 요청 바디 검증 테스트")
    void confirmNovelSectionRequestBody() throws Exception {
        // given
        NovelSectionCreateDTO sectionCreateDTO1 = NovelSectionCreateDTO.builder()
                .content("")
                .part(1)
                .build();
        NovelSectionCreateDTO sectionCreateDTO2 = NovelSectionCreateDTO.builder()
                .content("TTTTTTTTTTTTTTTTTEEEEEEEEEEEESSSSSSSSSSSSSSSTTTTTTTTTT")
                .part(null)
                .build();


        // when
        ResultActions resultAction1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/section/{novelId}", novel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sectionCreateDTO1))
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken() + "0")
        );
        ResultActions resultAction2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/section/{novelId}", novel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sectionCreateDTO2))
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        // then
        resultAction1.andExpect(status().isMethodNotAllowed());
        resultAction2.andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("소설 섹션 투표 요청 컨트롤러 테스트")
    protected void voteNovelSection1() throws Exception {
        // given
        NovelSectionCreateDTO novelSectionCreateDTO = NovelSectionMother.createDto();
        novelSectionService.createNovelSection(novel.getId(), member.getId(), novelSectionCreateDTO);
        List<NovelSection> novelSections = novelSectionRepository.findNovelSectionByNovel(novel).get();
        NovelSection novelSection = novelSections.get(0);

        // when
        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/section/vote/{novelSectionId}", novelSection.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        // then
        resultAction.andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("소설 섹션 중복 투표 테스트")
    protected void voteNovelSection2() throws Exception {
        // given
        NovelSectionCreateDTO novelSectionCreateDTO = NovelSectionMother.createDto();
        novelSectionService.createNovelSection(novel.getId(), member.getId(), novelSectionCreateDTO);
        List<NovelSection> novelSections = novelSectionRepository.findNovelSectionByNovel(novel).get();
        NovelSection novelSection = novelSections.get(0);

        // when
        ResultActions resultAction1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/section/vote/{novelSectionId}", novelSection.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );
        ResultActions resultAction2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/section/vote/{novelSectionId}", novelSection.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
        );

        // then
        resultAction1.andExpect(status().isAccepted());
        resultAction2.andExpect(status().isMethodNotAllowed());
    }
}