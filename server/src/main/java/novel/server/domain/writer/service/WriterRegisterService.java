package novel.server.domain.writer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import novel.server.domain.writer.Writer;
import novel.server.domain.writer.WriterRepository;
import novel.server.domain.writer.WriterService;
import novel.server.domain.writer.exception.WriterAlreadyExistsException;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class WriterRegisterService implements WriterService {
    private final WriterRepository writerRepository;

    /**
     * 회원 가입
     * @param writer
     */
    public String register(Writer writer) {
        if (writerRepository.findWriterByPenName(writer.getPenName()).isPresent()) {
            throw new WriterAlreadyExistsException("중복된 필명 입니다.");
        }
        writerRepository.save(writer);
        return "회원가입이 완료되었습니다.";
    }
}
