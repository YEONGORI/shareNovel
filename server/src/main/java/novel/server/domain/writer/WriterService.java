package novel.server.domain.writer;

import org.springframework.stereotype.Service;

@Service
public interface WriterService {
    String register(Writer writer);
}
