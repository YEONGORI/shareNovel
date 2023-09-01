package novel.server.part;

import lombok.RequiredArgsConstructor;
import novel.server.member.dto.CustomUser;
import novel.server.part.dto.PartUpdateReqDTO;
import novel.server.part.dto.PartCreateReqDTO;
import novel.server.part.dto.PartResDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/part")
@RequiredArgsConstructor
public class PartController {
    private final PartService partService;

    @PostMapping("/{novelId}")
    public ResponseEntity<?> createPart(
            @PathVariable Long novelId,
            @Validated @RequestBody PartCreateReqDTO requestDTO
    ) {
        Long memberId = getMemberId();
        PartResDTO part = partService.createPart(novelId, memberId, requestDTO);
        return makeResponseEntity(part, HttpStatus.CREATED);
    }

    @PatchMapping("/{novelId}/{partId}")
    public ResponseEntity<?> updatePart(
            @PathVariable Long novelId,
            @PathVariable Long partId,
            @Validated @RequestBody PartUpdateReqDTO requestDTO
    ) {
        Long memberId = getMemberId();
        PartResDTO part = partService.updatePart(novelId, memberId, partId, requestDTO);
        return makeResponseEntity(part, HttpStatus.OK);
    }

    @GetMapping("/{novelId}")
    public ResponseEntity<?> getPartsForNovel(@PathVariable Long novelId) {
        List<Part> parts = partService.getPartsForNovel(novelId);
        return makeResponseEntity(parts, HttpStatus.OK);
    }

    @GetMapping("/writer")
    public ResponseEntity<?> getPartsForWriter() {
        List<Part> parts = partService.getPartsForWriter(getMemberId());
        return makeResponseEntity(parts, HttpStatus.OK);
    }

    @DeleteMapping("/{novelId}/{partId}")
    public ResponseEntity<?> deletePartProposal(
            @PathVariable Long novelId,
            @PathVariable Long partId
    ) {
        partService.deletePartProposal(novelId, getMemberId(), partId);
        return makeResponseEntity("DELETED", HttpStatus.OK);
    }

    private static Long getMemberId() {
        return ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
    }

    private <T> ResponseEntity<T> makeResponseEntity(T responseBody, HttpStatus statusCode) {
        return new ResponseEntity<>(responseBody, statusCode);
    }
}
