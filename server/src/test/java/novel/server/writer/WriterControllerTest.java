package novel.server.writer;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        WriterDefaultRegisterDto registerDto = createRegisterDto("TEST", "PASSWORD");

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
        WriterDefaultRegisterDto registerDto = createRegisterDto("TEST", "PASSWORD");

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
        WriterDefaultRegisterDto registerDto0 = createRegisterDto("TEST", "PASSWORD");
        WriterDefaultRegisterDto registerDto1 = createRegisterDto("", "");
        WriterDefaultRegisterDto registerDto2 = createRegisterDto(null, "");
        WriterDefaultRegisterDto registerDto3 = createRegisterDto(null, null);

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
        WriterDefaultRegisterDto registerDto = createRegisterDto("TEST", "PASSWORD");
        WriterDefaultLoginDto loginDto = createLoginDto("TEST", "PASSWORD");


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
        WriterDefaultRegisterDto registerDto = createRegisterDto("TEST", "PASSWORD");
        WriterDefaultLoginDto loginDto1 = createLoginDto("TEST1", "PASSWORD");
        WriterDefaultLoginDto loginDto2 = createLoginDto("TEST", "PASSWORDS");


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
        WriterDefaultLoginDto loginDt1 = createLoginDto("", "");
        WriterDefaultLoginDto loginDt2 = createLoginDto(null, "");
        WriterDefaultLoginDto loginDt3 = createLoginDto(null, null);

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDt1)))
                .andExpect(status().isBadRequest()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDt2)))
                .andExpect(status().isBadRequest()); // then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/writer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDt3)))
                .andExpect(status().isBadRequest()); // then
    }

    private static WriterDefaultRegisterDto createRegisterDto(String penName, String password) {
        WriterDefaultRegisterDto registerDto = new WriterDefaultRegisterDto();
        registerDto.setPenName(penName);
        registerDto.setPassword(password);
        return registerDto;
    }

    private static WriterDefaultLoginDto createLoginDto(String penName, String password) {
        WriterDefaultLoginDto loginDto = new WriterDefaultLoginDto();
        loginDto.setPenName(penName);
        loginDto.setPassword(password);
        return loginDto;
    }
}