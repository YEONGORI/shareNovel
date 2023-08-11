
package novel.server.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import novel.server.writer.dto.WriterDefaultLoginDto;
import novel.server.writer.dto.WriterDefaultRegisterDto;
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
class WriterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원 가입 요청")
    void register() throws Exception {
        // given
        WriterDefaultRegisterDto registerDto = WriterMother.registerDto();
        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated()); // then
    }

    @Test
    @DisplayName("중복 회원 가입 요청")
    void dupRegister() throws Exception {
        // given
        WriterDefaultRegisterDto registerDto = WriterMother.registerDto();

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated());

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("회원 가입 요청 검증")
    void validateRegisterRequest() throws Exception {
        // given
        WriterDefaultRegisterDto registerDto0 = WriterMother.registerDto();
        WriterDefaultRegisterDto registerDto1 = WriterMother.registerDto();
        registerDto1.setPenName(null);
        registerDto1.setPassword(null);
        WriterDefaultRegisterDto registerDto2 = WriterMother.registerDto();
        registerDto2.setPassword("");
        WriterDefaultRegisterDto registerDto3 = WriterMother.registerDto();
        registerDto3.setPenName("");


        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto0)))
                .andExpect(status().isCreated()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto1)))
                .andExpect(status().isBadRequest()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto2)))
                .andExpect(status().isBadRequest()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto3)))
                .andExpect(status().isBadRequest()); // then
    }

    @Test
    @DisplayName("로그인 요청")
    void login() throws Exception {
        // given
        WriterDefaultRegisterDto registerDto = WriterMother.registerDto();
        WriterDefaultLoginDto loginDto = WriterDefaultLoginDto.builder()
                .penName(registerDto.getPenName())
                .password(registerDto.getPassword())
                .build();



        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isAccepted()); // then
    }

    @Test
    @DisplayName("인증되지 않은 로그인 요청")
    void loginNotAuthorized() throws Exception {
        // given
        WriterDefaultRegisterDto registerDto = WriterMother.registerDto();
        WriterDefaultLoginDto loginDto1 = WriterMother.loginDto();
        loginDto1.setPenName(registerDto.getPenName());
        WriterDefaultLoginDto loginDto2 = WriterMother.loginDto();
        loginDto2.setPassword(registerDto.getPassword());

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto1)))
                .andExpect(status().isForbidden()); // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto2)))
                .andExpect(status().isForbidden()); // then
    }

    @Test
    @DisplayName("로그인 요청 검증")
    void validateLoginRequest() throws Exception {
        // given
        WriterDefaultLoginDto loginDto1 = WriterMother.loginDto();
        loginDto1.setPenName("");
        loginDto1.setPassword("");
        WriterDefaultLoginDto loginDto2 = WriterMother.loginDto();
        loginDto2.setPassword(null);
        WriterDefaultLoginDto loginDto3 = WriterMother.loginDto();
        loginDto3.setPenName(null);

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto1)))
                .andExpect(status().isBadRequest()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto2)))
                .andExpect(status().isBadRequest()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto3)))
                .andExpect(status().isBadRequest()); // then
    }
}