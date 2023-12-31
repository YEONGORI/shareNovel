package novel.server.novel.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import novel.server.novel.Novel;
import novel.server.novel.NovelRepository;
import novel.server.novel.NovelService;
import novel.server.novel.dto.NovelRegisterDTO;
import novel.server.stake.Stake;
import novel.server.writer.Writer;
import novel.server.writer.WriterRepository;
import novel.server.writernovel.WriterNovel;
import novel.server.writernovel.WriterNovelRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class NovelServiceImpl implements NovelService {
    private final NovelRepository novelRepository;
    private final WriterRepository writerRepository;
    private final WriterNovelRepository writerNovelRepository;

    @Override
    public Novel register(NovelRegisterDTO registerDto) {
        Novel novel = registerDto.toEntity();

        Writer writer = writerRepository.findWriterByPenName(registerDto.getPenName())
                .orElseThrow(() -> new BadCredentialsException("등록되지 않은 사용자입니다."));

        WriterNovel writerNovel = WriterNovel.builder()
                .writer(writer)
                .novel(novel)
                .build();

        Stake stake = Stake.builder()
                .writer(writer)
                .novel(novel)
                .stakeValue(1.0)
                .build();

        novel.getWriterNovels().add(writerNovel);
        novel.getStakes().add(stake);
        writer.getWriterNovels().add(writerNovel);
        novelRepository.save(novel);
        writerNovelRepository.save(writerNovel);
        return novel;
    }
}
