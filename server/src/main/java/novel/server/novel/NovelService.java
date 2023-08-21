package novel.server.novel;

import novel.server.novel.dto.NovelRegisterDTO;
import org.springframework.stereotype.Service;


@Service
public interface NovelService {
    Novel register(NovelRegisterDTO registerDto);
}
