package novel.server.novel.service;

import lombok.RequiredArgsConstructor;
import novel.server.novel.Novel;
import novel.server.novel.NovelRepository;
import novel.server.novel.NovelService;
import novel.server.novel.dto.NovelRegisterDto;
import novel.server.writer.Writer;
import novel.server.writer.WriterRepository;
import novel.server.writernovel.WriterNovel;
import novel.server.writernovel.WriterNovelRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NovelServiceImpl implements NovelService {
    private final NovelRepository novelRepository;
    private final WriterRepository writerRepository;
    private final WriterNovelRepository writerNovelRepository;

    @Override
    public void register(NovelRegisterDto registerDto) {
        Novel novel = registerDto.toEntity();
        novelRepository.save(novel);

        Writer writer = writerRepository.findWriterByPenName(registerDto.getPenName())
                .orElseThrow(() -> new BadCredentialsException("등록되지 않은 사용자입니다."));

        WriterNovel writerNovel = WriterNovel.builder()
                .writer(writer)
                .novel(novel)
                .build();

        novel.getWriterNovels().add(writerNovel);
        writer.getWriterNovels().add(writerNovel);
        writerNovelRepository.save(writerNovel);
    }
}
