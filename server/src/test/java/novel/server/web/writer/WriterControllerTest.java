package novel.server.web.writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import novel.server.domain.writer.WriterService;
import novel.server.domain.writer.exception.WriterAlreadyExistsException;
import novel.server.web.writer.dto.WriterRegisterDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
        WriterRegisterDto registerDto = createRegisterDto("TEST", "PASSWORD");

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
        WriterRegisterDto registerDto = createRegisterDto("TEST", "PASSWORD");

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
    @DisplayName("요청 Body 검증")
    void validateRequestBody() throws Exception {
        // given
        WriterRegisterDto registerDto0 = createRegisterDto("TEST", "PASSWORD");
        WriterRegisterDto registerDto1 = createRegisterDto("", "");
        WriterRegisterDto registerDto2 = createRegisterDto(null, "");
        WriterRegisterDto registerDto3 = createRegisterDto(null, null);

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

    private static WriterRegisterDto createRegisterDto(String penName, String password) {
        WriterRegisterDto body = new WriterRegisterDto();
        body.setPenName(penName);
        body.setPassword(password);
        return body;
    }
}