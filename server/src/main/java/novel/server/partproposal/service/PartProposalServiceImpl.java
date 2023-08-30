package novel.server.partproposal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import novel.server.member.Member;
import novel.server.member.MemberRepository;
import novel.server.member.exception.MemberNotExistsException;
import novel.server.novel.Novel;
import novel.server.novel.NovelRepository;
import novel.server.novel.exception.NovelNotExistsException;
import novel.server.partproposal.PartProposal;
import novel.server.partproposal.PartProposalRepository;
import novel.server.partproposal.PartProposalService;
import novel.server.partproposal.dto.PartProposalPatchReqDTO;
import novel.server.partproposal.dto.PartProposalPatchResDTO;
import novel.server.partproposal.dto.PartProposalPostReqDTO;
import novel.server.partproposal.dto.PartProposalPostResDTO;
import novel.server.partproposal.exception.PartProposalNotExistsException;
import novel.server.writer.Writer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PartProposalServiceImpl implements PartProposalService {
    private final PartProposalRepository partProposalRepository;
    private final NovelRepository novelRepository;
    private final MemberRepository memberRepository;

    @Override
    public PartProposalPostResDTO createPartProposal(Long novelId, Long memberId, PartProposalPostReqDTO requestDTO) {
        Novel novel = novelRepository.findNovelById(novelId)
                .orElseThrow(() -> new NovelNotExistsException("소설을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));
        Writer writer = member.getWriter();

        PartProposal partProposal = PartProposal.builder()
                .content(requestDTO.getContent())
                .likes(new ArrayList<>())
                .novel(novel)
                .part(requestDTO.getPart())
                .writer(writer)
                .build();

        PartProposal save = partProposalRepository.save(partProposal);
        return PartProposalPostResDTO.fromEntity(save);
    }

    @Override
    public PartProposalPatchResDTO updatePartProposal(Long novelId, Long memberId, Long partProposalId, PartProposalPatchReqDTO requestDTO) {
        Novel novel = novelRepository.findNovelById(novelId)
                .orElseThrow(() -> new NovelNotExistsException("소설을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));
        PartProposal partProposal = partProposalRepository.findPartProposalById(partProposalId)
                .orElseThrow(() -> new PartProposalNotExistsException("파트 제안안을 찾을 수 없습니다."));


    }

    @Override
    public List<PartProposal> getPartProposalsForWriter(Long novelId, Long memberId) {
        return null;
    }

    @Override
    public List<PartProposal> getPartProposalsForPart(Long novelId, Long partProposalId) {
        return null;
    }

    @Override
    public void deletePartProposal(Long novelId, Long memberId, Long partProposalId) {

    }

//    @Override
//    public PartProposalPostResDTO createNovelSection(Long novelId, Long memberId, PartProposalPostReqDTO createDTO) {
//        Novel novel = novelRepository.findNovelById(novelId)
//                .orElseThrow(() -> new NovelNotExistsException("소설을 찾을 수 없습니다."));
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));
//
//        Writer writer = member.getWriter();
//
//        PartProposal partProposal = createDTO.toEntity();
//        partProposal.setNovel(novel);
//        partProposal.setWriter(writer);
//        writer.getPartProposals().add(partProposal);
//
//        PartProposal savedPartProposal = partProposalRepository.save(partProposal);
//        return PartProposalPostResDTO.fromEntity(savedPartProposal);
//    }


}
