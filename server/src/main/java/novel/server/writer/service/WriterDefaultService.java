package novel.server.writer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import novel.server.writer.Writer;
import novel.server.writer.WriterRepository;
import novel.server.writer.WriterService;
import novel.server.writer.auth.TokenInfo;
import novel.server.writer.dto.WriterDefaultLoginDto;
import novel.server.writer.dto.WriterDefaultRegisterDto;
import novel.server.writer.exception.WriterAlreadyExistsException;
import novel.server.writer.auth.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class WriterDefaultService implements WriterService {
    private final WriterRepository writerRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     * @param registerDto
     * @return
     */
    public String register(WriterDefaultRegisterDto registerDto) {
        if (writerRepository.findWriterByPenName(registerDto.getPenName()).isPresent()) {
            throw new WriterAlreadyExistsException("사용중인 필명 입니다.");
        }
        writerRepository.save(registerDto.toEntity());
        return "회원가입이 완료되었습니다.";
    }

    /**
     * 로그인
     * @param loginDto
     * @return
     */
    public TokenInfo login(WriterDefaultLoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getPenName(), loginDto.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authenticate);
    }
}
