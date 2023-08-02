package novel.server.web.writer;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import novel.server.domain.writer.WriterService;
import novel.server.web.writer.dto.WriterRegisterDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ResponseBody
@RestController
@RequestMapping("/api/writer")
@RequiredArgsConstructor
public class WriterController {
    private final WriterService writerService;

    @PostMapping("/register")
    public ResponseEntity register(
            @Validated @RequestBody WriterRegisterDto writerRegisterDto,
            HttpServletResponse response
    ) {
        String resultMsg = writerService.register(writerRegisterDto.toEntity());
        response.setHeader("Location", "/login");
        return makeReponseEntity(resultMsg, HttpStatus.CREATED);
    }

    private <T> ResponseEntity<T> makeReponseEntity(T responseBody, HttpStatus statusCode) {
        return new ResponseEntity<>(responseBody, statusCode);
    }
}
