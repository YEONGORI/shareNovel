package novel.server.writer;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import novel.server.writer.auth.TokenInfo;
import novel.server.writer.dto.WriterDefaultLoginDto;
import novel.server.writer.dto.WriterDefaultRegisterDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ResponseBody
@RestController
@RequestMapping("/api/writer")
@RequiredArgsConstructor
public class WriterController {
    private final WriterService writerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Validated @RequestBody WriterDefaultRegisterDto writerDefaultRegisterDto,
            HttpServletResponse response
    ) {
        String resultMsg = writerService.register(writerDefaultRegisterDto);
        response.setHeader("Location", "/login");
        return makeReponseEntity(resultMsg, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Validated @RequestBody WriterDefaultLoginDto writerDefaultLoginDto,
            HttpServletResponse response
    ) {
        TokenInfo tokenInfo = writerService.login(writerDefaultLoginDto);
        return makeReponseEntity(tokenInfo, HttpStatus.ACCEPTED);
    }

    private <T> ResponseEntity<T> makeReponseEntity(T responseBody, HttpStatus statusCode) {
        return new ResponseEntity<>(responseBody, statusCode);
    }

}
