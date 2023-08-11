package novel.server.novel.service;

import novel.server.novel.Novel;
import novel.server.novel.NovelMother;
import novel.server.novel.NovelRepository;
import novel.server.novel.dto.NovelRegisterDto;
import novel.server.writer.Writer;
import novel.server.writer.WriterMother;
import novel.server.writer.WriterRepository;
import novel.server.writer.dto.WriterDefaultRegisterDto;
import novel.server.writernovel.WriterNovel;
import novel.server.writernovel.WriterNovelRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
class NovelServiceImplTest {
    @Autowired
    NovelRepository novelRepository;
    @Autowired
    WriterRepository writerRepository;
    @Autowired
    WriterNovelRepository writerNovelRepository;

    @Test
    @DisplayName("소설 등록")
    void register() {
        // given
        WriterDefaultRegisterDto writerRegisterDto = WriterMother.registerDto();
        Writer writer = writerRegisterDto.toEntity();
        writerRepository.save(writer);

        NovelRegisterDto registerDto = NovelMother.registerDto();
        Novel novel = registerDto.toEntity();
        novelRepository.save(novel);

        // when
        WriterNovel writerNovel = WriterNovel.builder()
                .writer(writer)
                .novel(novel)
                .build();
        writerNovelRepository.save(writerNovel);

        // then
        Optional<WriterNovel> byNovel = writerNovelRepository.findByNovel(novel);
        Optional<WriterNovel> byWriter = writerNovelRepository.findByWriter(writer);
        assertThat(byNovel.get()).isEqualTo(byWriter.get());
    }

    @Test
    @DisplayName("소설 - 작가 매핑 테스트")
    void register_mapping() {
        // given
        WriterDefaultRegisterDto writerRegisterDto = WriterMother.registerDto();
        Writer writer = writerRegisterDto.toEntity();
        writerRepository.save(writer);

        NovelRegisterDto registerDto = NovelMother.registerDto();
        Novel novel = registerDto.toEntity();
        novelRepository.save(novel);

        // when
        WriterNovel writerNovel = WriterNovel.builder()
                .writer(writer)
                .novel(novel)
                .build();
        writerNovelRepository.save(writerNovel);

        // then
        Optional<WriterNovel> byNovel = writerNovelRepository.findByNovel(novel);
        Optional<WriterNovel> byWriter = writerNovelRepository.findByWriter(writer);
        assertThat(byNovel.get()).isEqualTo(byWriter.get());
    }
}