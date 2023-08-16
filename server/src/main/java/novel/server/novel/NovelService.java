package novel.server.novel;

import novel.server.novel.dto.NovelRegisterDto;
import org.springframework.stereotype.Service;


@Service
public interface NovelService {
    Novel register(NovelRegisterDto registerDto);
}
