package novel.server.novelsection;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import novel.server.member.dto.CustomUser;
import novel.server.novelsection.dto.NovelSectionCreateDTO;
import novel.server.novelsection.dto.NovelSectionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequestMapping("/api/section")
@RequiredArgsConstructor
public class NovelSectionController {
    private final NovelSectionService novelSectionService;

    @PostMapping("/{novelId}")
    public ResponseEntity<?> createNovelSection(
            @PathVariable Long novelId,
            @Validated @RequestBody NovelSectionCreateDTO createDTO,
            HttpServletResponse response
    ) {
        Long memberId = getMemberId();
        NovelSectionResponseDTO novelSectionResponseDTO = novelSectionService.createNovelSection(novelId, memberId, createDTO);
        return makeResponseEntity(novelSectionResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/vote/{novelSectionId}")
    public ResponseEntity<?> voteNovelSection(
            @PathVariable Long novelSectionId,
            HttpServletResponse response
    ) {
        Long memberId = getMemberId();
        novelSectionService.voteNovelSection(novelSectionId, memberId);
        return makeResponseEntity("ok", HttpStatus.ACCEPTED);
    }

    private static Long getMemberId() {
        return ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
    }

    private <T> ResponseEntity<T> makeResponseEntity(T responseBody, HttpStatus statusCode) {
        return new ResponseEntity<>(responseBody, statusCode);
    }
}
