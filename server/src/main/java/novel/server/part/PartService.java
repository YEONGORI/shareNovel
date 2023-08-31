package novel.server.part;

import novel.server.part.dto.PartUpdateReqDTO;
import novel.server.part.dto.PartCreateReqDTO;
import novel.server.part.dto.PartResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PartService {
    PartResDTO createPart(Long novelId, Long memberId, PartCreateReqDTO requestDTO);

    PartResDTO updatePart(Long novelId, Long memberId, Long partId, PartUpdateReqDTO requestDTO);

    List<Part> getPartsForNovel(Long novelId);

    List<Part> getPartsForWriter(Long memberId);

    void deletePartProposal(Long novelId, Long memberId, Long partId);
}
