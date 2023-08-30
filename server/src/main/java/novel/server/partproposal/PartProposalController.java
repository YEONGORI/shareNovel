package novel.server.partproposal;

import lombok.RequiredArgsConstructor;
import novel.server.member.dto.CustomUser;
import novel.server.partproposal.dto.PartProposalPatchReqDTO;
import novel.server.partproposal.dto.PartProposalPatchResDTO;
import novel.server.partproposal.dto.PartProposalPostReqDTO;
import novel.server.partproposal.dto.PartProposalPostResDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/section")
@RequiredArgsConstructor
public class PartProposalController {
    private final PartProposalService partProposalService;

    @PostMapping("/{novelId}")
    public ResponseEntity<?> createPartProposal(
            @PathVariable Long novelId,
            @Validated @RequestBody PartProposalPostReqDTO requestDTO
    ) {
        Long memberId = getMemberId();
        PartProposalPostResDTO partProposal = partProposalService.createPartProposal(novelId, memberId, requestDTO);
        return makeResponseEntity(partProposal, HttpStatus.CREATED);
    }

    @PatchMapping("/{novelId}/{partId}")
    public ResponseEntity<?> updatePartProposal(
            @PathVariable Long novelId,
            @PathVariable Long partId,
            @Validated @RequestBody PartProposalPatchReqDTO requestDTO
    ) {
        Long memberId = getMemberId();
        PartProposalPatchResDTO partProposal = partProposalService.updatePartProposal(novelId, memberId, partId, requestDTO);
        return makeResponseEntity(partProposal, HttpStatus.OK);
    }

    @GetMapping("/{novelId}")
    public ResponseEntity<?> getPartProposalsForWriter(@PathVariable Long novelId) {
        List<PartProposal> partProposals = partProposalService.getPartProposalsForWriter(novelId, getMemberId());
        return makeResponseEntity(partProposals, HttpStatus.OK);
    }

    @GetMapping("/{novelId}/{partId}")
    public ResponseEntity<?> getPartProposalsForPart(
            @PathVariable Long novelId,
            @PathVariable Long partId
    ) {
        List<PartProposal> partProposals = partProposalService.getPartProposalsForPart(novelId, partId);
        return makeResponseEntity(partProposals, HttpStatus.OK);
    }

    @DeleteMapping("/{novelId}/{partId}")
    public ResponseEntity<?> deletePartProposal(
            @PathVariable Long novelId,
            @PathVariable Long partId
    ) {
        partProposalService.deletePartProposal(novelId, getMemberId(), partId);
        return makeResponseEntity("DELETED", HttpStatus.OK);
    }

    private static Long getMemberId() {
        return ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
    }

    private <T> ResponseEntity<T> makeResponseEntity(T responseBody, HttpStatus statusCode) {
        return new ResponseEntity<>(responseBody, statusCode);
    }
}
