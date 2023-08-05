package novel.server.writer;

import novel.server.writer.auth.TokenInfo;
import novel.server.writer.dto.WriterDefaultLoginDto;
import novel.server.writer.dto.WriterDefaultRegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface WriterService {
    String register(WriterDefaultRegisterDto registerDto);
    TokenInfo login(WriterDefaultLoginDto loginDto);
}
