package novel.server.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import novel.server.member.dto.MemberDefaultLoginDTO;
import novel.server.member.dto.MemberDefaultRegisterDTO;
import novel.server.writer.Writer;
import novel.server.writer.WriterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WriterRepository writerRepository;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 가입 요청")
    void register() throws Exception {
        // given
        MemberDefaultRegisterDTO registerDto = MemberMother.registerDto();
        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated()); // then
    }

    @Test
    @DisplayName("중복 회원 가입 요청")
    void dupRegister() throws Exception {
        // given
        MemberDefaultRegisterDTO registerDto = MemberMother.registerDto();

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated());

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("회원 가입 요청 검증")
    void validateRegisterRequest() throws Exception {
        // given
        MemberDefaultRegisterDTO registerDto0 = MemberMother.registerDto();
        MemberDefaultRegisterDTO registerDto1 = MemberDefaultRegisterDTO.builder()
                .penName(null)
                .password(null)
                .build();
        MemberDefaultRegisterDTO registerDto2 = MemberDefaultRegisterDTO.builder()
                .penName("")
                .password("password")
                .build();
        MemberDefaultRegisterDTO registerDto3 = MemberDefaultRegisterDTO.builder()
                .penName("penName")
                .password("")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto0)))
                .andExpect(status().isCreated()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto1)))
                .andExpect(status().isBadRequest()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto2)))
                .andExpect(status().isBadRequest()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto3)))
                .andExpect(status().isBadRequest()); // then
    }

    @Test
    @DisplayName("회원가입시 Member - Writer 1:1 양방향 매칭 검증")
    void MemberWriterOneToOne() {
        // given
        MemberDefaultRegisterDTO registerDto = MemberMother.registerDto();

        // when
        memberService.register(registerDto);
        Member member = memberRepository.findMemberByPenName(registerDto.getPenName()).get();
        Writer writer = writerRepository.findWriterByPenName(registerDto.getPenName()).get();

        // then
        Assertions.assertThat(member.getId()).isEqualTo(writer.getMember().getId());
        Assertions.assertThat(writer.getId()).isEqualTo(member.getWriter().getId());
        Assertions.assertThat(member).isEqualTo(writer.getMember());
    }

    @Test
    @DisplayName("로그인 요청")
    void login() throws Exception {
        // given
        MemberDefaultRegisterDTO registerDto = MemberMother.registerDto();
        MemberDefaultLoginDTO loginDto = MemberDefaultLoginDTO.builder()
                .penName(registerDto.getPenName())
                .password(registerDto.getPassword())
                .build();



        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isAccepted()); // then
    }

    @Test
    @DisplayName("인증되지 않은 로그인 요청")
    void loginNotAuthorized() throws Exception {
        // given
        MemberDefaultRegisterDTO registerDto = MemberMother.registerDto();
        MemberDefaultLoginDTO loginDto1 = MemberDefaultLoginDTO.builder()
                .penName(registerDto.getPenName())
                .password("TEST")
                .build();
        MemberDefaultLoginDTO loginDto2 = MemberDefaultLoginDTO.builder()
                .penName("TEST")
                .password(registerDto.getPassword())
                .build();


        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto1)))
                .andExpect(status().isForbidden()); // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto2)))
                .andExpect(status().isForbidden()); // then
    }

    @Test
    @DisplayName("로그인 요청 검증")
    void validateLoginRequest() throws Exception {
        // given
        MemberDefaultLoginDTO loginDto1 = MemberDefaultLoginDTO.builder()
                .penName("").password("").build();

        MemberDefaultLoginDTO loginDto2 = MemberDefaultLoginDTO.builder()
                .penName(null).password("TEST").build();

        MemberDefaultLoginDTO loginDto3 = MemberDefaultLoginDTO.builder()
                .penName("TEST").password(null).build();

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto1)))
                .andExpect(status().isBadRequest()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto2)))
                .andExpect(status().isBadRequest()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto3)))
                .andExpect(status().isBadRequest()); // then
    }
}