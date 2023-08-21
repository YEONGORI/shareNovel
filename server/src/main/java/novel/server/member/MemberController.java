package novel.server.member;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import novel.server.member.auth.TokenInfo;
import novel.server.member.dto.MemberDefaultLoginDTO;
import novel.server.member.dto.MemberDefaultRegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ResponseBody
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Validated @RequestBody MemberDefaultRegisterDTO memberDefaultRegisterDto,
            HttpServletResponse response
    ) {
        memberService.register(memberDefaultRegisterDto);
        response.setHeader("Location", "/login");
        return makeReponseEntity("ok", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Validated @RequestBody MemberDefaultLoginDTO memberDefaultLoginDto
    ) {
        TokenInfo tokenInfo = memberService.login(memberDefaultLoginDto);
        return makeReponseEntity(tokenInfo, HttpStatus.ACCEPTED);
    }

    private <T> ResponseEntity<T> makeReponseEntity(T responseBody, HttpStatus statusCode) {
        return new ResponseEntity<>(responseBody, statusCode);
    }

}
