package novel.server.domain.writer.service;

import novel.server.domain.writer.Writer;
import novel.server.domain.writer.WriterRepository;
import novel.server.domain.writer.WriterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class WriterRegisterServiceTest {
    @Autowired
    WriterRepository writerRepository;

    @Test
    @DisplayName("회원 가입")
    void register() {
        String penName = "testPenName";
        String password = "testPassword";
        Writer writer1 = new Writer(penName, password);
        writerRepository.save(writer1);

        Writer writer2 = writerRepository.findWriterByPenName(penName).get();
        assertThat(writer1).isEqualTo(writer2);
    }
}