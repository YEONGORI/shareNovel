package novel.server.vote;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import novel.server.member.dto.CustomUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequestMapping("/api/vote")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<?> voteForNovelSection(
            @PathVariable Long novelSectionId,
            HttpServletResponse response
    ) {
        Long memberId = getMemberId();
        voteService.voteForNovelSection(memberId, novelSectionId);
        return makeResponseEntity("ok", HttpStatus.ACCEPTED);
    }

    private static Long getMemberId() {
        return ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
    }

    private <T> ResponseEntity<T> makeResponseEntity(T responseBody, HttpStatus statusCode) {
        return new ResponseEntity<>(responseBody, statusCode);
    }
}
