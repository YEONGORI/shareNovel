package novel.server.writernovel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterNovelRepository extends JpaRepository<WriterNovel, Long> {
}
