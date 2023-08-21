package novel.server.novelsection;

import novel.server.novel.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NovelSectionRepository extends JpaRepository<NovelSection, Long> {
    Optional<NovelSection> findNovelSectionById(Long id);

    Optional<List<NovelSection>> findNovelSectionByNovel(Novel novel);
}
