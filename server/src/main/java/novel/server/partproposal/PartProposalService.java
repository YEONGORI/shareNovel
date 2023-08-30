package novel.server.partproposal;

import novel.server.partproposal.dto.PartProposalPatchReqDTO;
import novel.server.partproposal.dto.PartProposalPatchResDTO;
import novel.server.partproposal.dto.PartProposalPostReqDTO;
import novel.server.partproposal.dto.PartProposalPostResDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PartProposalService {
    PartProposalPostResDTO createPartProposal(Long novelId, Long memberId, PartProposalPostReqDTO requestDTO);

    PartProposalPatchResDTO updatePartProposal(Long novelId, Long memberId, Long partProposalId, PartProposalPatchReqDTO requestDTO);

    List<PartProposal> getPartProposalsForWriter(Long novelId, Long memberId);

    List<PartProposal> getPartProposalsForPart(Long novelId, Long partProposalId);

    void deletePartProposal(Long novelId, Long memberId, Long partProposalId);
}
