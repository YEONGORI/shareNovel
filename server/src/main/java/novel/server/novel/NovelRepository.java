package novel.server.novel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Long> {
    Optional<Novel> findNovelById(Long id);
}
