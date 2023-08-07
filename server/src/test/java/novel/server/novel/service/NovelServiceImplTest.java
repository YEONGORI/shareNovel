package novel.server.novel.service;

import novel.server.novel.Novel;
import novel.server.novel.NovelRepository;
import novel.server.novel.dto.NovelRegisterDto;
import novel.server.writer.WriterRepository;
import novel.server.writer.dto.WriterDefaultRegisterDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class NovelServiceImplTest {
    private static final String penName = "testPenName";

    @Autowired
    NovelRepository novelRepository;
    @Autowired
    WriterRepository writerRepository;

    @Test
    @DisplayName("소설 등록")
    void register() {
        // given
        NovelRegisterDto registerDto = createNovelRegisterDto();
        Novel novel = registerDto.toEntity();
        novelRepository.save(novel);

        // when
        writerRepository.findWriterByPenName(registerDto.getPenName())

        // then
    }

    private static WriterDefaultRegisterDto createWriterRegisterDto(String password) {
        WriterDefaultRegisterDto registerDto = new WriterDefaultRegisterDto();
        registerDto.setPenName(penName);
        registerDto.setPassword(password);
        return registerDto;
    }


    public static NovelRegisterDto createNovelRegisterDto() {
        NovelRegisterDto registerDto = new NovelRegisterDto();
        registerDto.setTitle("testTitle");
        registerDto.setPlot("testTitle");
        registerDto.setTheme("testTheme");
        registerDto.setCharacters("testCharacters");
        registerDto.setBackground("testBackground");
        registerDto.setEvent("testEvent");
        registerDto.setPenName(penName);
    }
}