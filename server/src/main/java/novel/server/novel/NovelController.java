package novel.server.novel;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import novel.server.novel.dto.NovelRegisterDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/novel")
@RequiredArgsConstructor
public class NovelController {
    private final NovelService novelService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Validated @RequestBody NovelRegisterDto novelRegisterDto,
            HttpServletResponse response
    ) {
        novelService.register(novelRegisterDto);
        response.setHeader("Location", "/register");
        return makeResponseEntity(novelRegisterDto, HttpStatus.CREATED);
    }


    private <T> ResponseEntity<T> makeResponseEntity(T responseBody, HttpStatus statusCode) {
        return new ResponseEntity<>(responseBody, statusCode);
    }

}