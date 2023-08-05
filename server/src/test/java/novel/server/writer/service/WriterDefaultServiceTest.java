package novel.server.writer.service;

import novel.server.writer.Writer;
import novel.server.writer.WriterRepository;
import novel.server.writer.auth.JwtTokenProvider;
import novel.server.writer.dto.WriterDefaultLoginDto;
import novel.server.writer.dto.WriterDefaultRegisterDto;
import novel.server.writer.exception.WriterAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class WriterDefaultServiceTest {
    private final String penName = "testPenName";
    private final String password = "testPassword";

    @Autowired
    WriterRepository writerRepository;
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("회원 가입")
    void register() {

        Writer writer = createRegisterDto(penName, password).toEntity();
        writerRepository.save(writer);

        Writer writer_ch = writerRepository.findWriterByPenName(writer.getPenName()).get();
        assertThat(writer).isEqualTo(writer_ch);
    }

    @Test
    @DisplayName("로그인")
    void login() {
        // given
        Writer writer = createRegisterDto(penName, password).toEntity();
        WriterDefaultLoginDto loginDto = createLoginDto(penName, password);
        writerRepository.save(writer);

        // when
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getPenName(), loginDto.getPassword());

        // then
        Authentication authentication = assertDoesNotThrow(() -> authenticationManagerBuilder.getObject().authenticate(authenticationToken));
        assertDoesNotThrow(() -> jwtTokenProvider.createToken(authentication));
    }

    @Test
    @DisplayName("비인증 사용자 확인 [미등록 사용자]")
    void NotAuthorized1() {
        WriterDefaultLoginDto loginDto = createLoginDto(penName, password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getPenName(), loginDto.getPassword());
        assertThrows(AuthenticationException.class,
                () -> authenticationManagerBuilder.getObject().authenticate(authenticationToken));
    }

    @Test
    @DisplayName("비인증 사용자 확인 [필명, 비밀번호 불일치]")
    void NotAuthorized2() {
        Writer writer = createRegisterDto(penName, password).toEntity();
        writerRepository.save(writer);

        WriterDefaultLoginDto loginDto0 = createLoginDto(penName, password);
        WriterDefaultLoginDto loginDto1 = createLoginDto("SomePenName", password);
        WriterDefaultLoginDto loginDto2 = createLoginDto(penName, "somePassword");

        UsernamePasswordAuthenticationToken authenticationToken0 = new UsernamePasswordAuthenticationToken(loginDto0.getPenName(), loginDto0.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken1 = new UsernamePasswordAuthenticationToken(loginDto1.getPenName(), loginDto1.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken2 = new UsernamePasswordAuthenticationToken(loginDto2.getPenName(), loginDto2.getPassword());

        assertDoesNotThrow(() -> authenticationManagerBuilder.getObject().authenticate(authenticationToken0));
        assertThrows(AuthenticationException.class,
                () -> authenticationManagerBuilder.getObject().authenticate(authenticationToken1));
        assertThrows(AuthenticationException.class,
                () -> authenticationManagerBuilder.getObject().authenticate(authenticationToken2));
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