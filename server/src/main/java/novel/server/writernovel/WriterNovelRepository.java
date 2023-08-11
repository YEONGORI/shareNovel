package novel.server.writernovel;

import novel.server.novel.Novel;
import novel.server.writer.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WriterNovelRepository extends JpaRepository<WriterNovel, Long> {
    Optional<WriterNovel> findByWriter(Writer writer);
    Optional<WriterNovel> findByNovel(Novel novel);
}
