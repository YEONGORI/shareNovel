package novel.server.domain.writer;

import novel.server.domain.writer.exception.WriterAlreadyExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class WriterServiceTest {
    @Autowired
    WriterService writerService;

    @Test
    @DisplayName("중복 회원 가입")
    void register() {
        String penName = "testPenName";
        String password = "testPassword";
        Writer writer1 = new Writer(penName, password);
        writerService.register(writer1);

        Writer writer2 = new Writer(penName, password);
        assertThrows(WriterAlreadyExistsException.class, () -> writerService.register(writer2));
    }
}