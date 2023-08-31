package novel.server.part.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import novel.server.member.Member;
import novel.server.member.MemberRepository;
import novel.server.member.exception.MemberNotExistsException;
import novel.server.novel.Novel;
import novel.server.novel.NovelRepository;
import novel.server.novel.exception.NovelNotExistsException;
import novel.server.part.Part;
import novel.server.part.PartRepository;
import novel.server.part.PartService;
import novel.server.part.dto.PartUpdateReqDTO;
import novel.server.part.dto.PartCreateReqDTO;
import novel.server.part.dto.PartResDTO;
import novel.server.part.exception.PartNotExistsException;
import novel.server.part.exception.PartWriterNotMatchedException;
import novel.server.writer.Writer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final NovelRepository novelRepository;
    private final MemberRepository memberRepository;

    @Override
    public PartResDTO createPart(Long novelId, Long memberId, PartCreateReqDTO requestDTO) {
        Novel novel = novelRepository.findNovelById(novelId)
                .orElseThrow(() -> new NovelNotExistsException("소설을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));

        Writer writer = member.getWriter();
        Integer maxPartNum = partRepository.findMaxPartNumByNovel(novelId);

        Part part = Part.builder()
                .partNum(setMaxPartNum(maxPartNum))
                .content(requestDTO.getContent())
                .likes(new ArrayList<>())
                .novel(novel)
                .writer(writer)
                .build();

        Part saved = partRepository.save(part);
        return PartResDTO.fromEntity(saved);
    }


    @Override
    public PartResDTO updatePart(Long novelId, Long memberId, Long partId, PartUpdateReqDTO requestDTO) {
        novelRepository.findNovelById(novelId)
                .orElseThrow(() -> new NovelNotExistsException("소설을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));
        Part part = partRepository.findPartById(partId)
                .orElseThrow(() -> new PartNotExistsException("파트를 찾을 수 없습니다."));
        if (member.getWriter() != part.getWriter())
            throw new PartWriterNotMatchedException("사용자와 파트 작성자가 일치하지 않습니다.");

        part.updatePartContent(requestDTO.getContent());

        Part saved = partRepository.save(part);
        return PartResDTO.fromEntity(saved);
    }

    @Override
    public List<Part> getPartsForNovel(Long novelId) {
        Novel novel = novelRepository.findNovelById(novelId)
                .orElseThrow(() -> new NovelNotExistsException("소설을 찾을 수 없습니다."));

        return partRepository.findPartsByNovel(novel);
    }

    @Override
    public List<Part> getPartsForWriter(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("사용자를 찾을 수 없습니다."));
        Writer writer = member.getWriter();

        return partRepository.findPartsByWriter(writer);
    }

    @Override
    public void deletePartProposal(Long novelId, Long memberId, Long partId) {

    }

    private static int setMaxPartNum(Integer maxPartNum) {
        return (maxPartNum != null ? maxPartNum : 0) + 1;
    }
}
