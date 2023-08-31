package novel.server.part;

import novel.server.novel.Novel;
import novel.server.writer.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Optional<Part> findPartById(Long id);

    List<Part> findPartsByNovel(Novel novel);

    List<Part> findPartsByWriter(Writer writer);

    @Query("SELECT MAX(p.partNum) FROM Part p WHERE p.novel.id = :novelId")
    Integer findMaxPartNumByNovel(@Param("novelId") Long novelId);
}
