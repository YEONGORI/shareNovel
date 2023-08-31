package novel.server.like;

import lombok.RequiredArgsConstructor;
import novel.server.member.dto.CustomUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{partId}")
    public ResponseEntity<?> likeForPart(
            @PathVariable Long partId
    ) {
        Long memberId = getMemberId();
        likeService.likeForPart(memberId, partId);
        return makeResponseEntity("OK", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{partId}")
    public ResponseEntity<?> cancelLikeForPart(
            @PathVariable Long partId
    ) {
        Long memberId = getMemberId();
        likeService.cancelLikeForPart(memberId, partId);
        return makeResponseEntity("OK", HttpStatus.ACCEPTED);
    }

    private static Long getMemberId() {
        return ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
    }

    private <T> ResponseEntity<T> makeResponseEntity(T responseBody, HttpStatus statusCode) {
        return new ResponseEntity<>(responseBody, statusCode);
    }
}
