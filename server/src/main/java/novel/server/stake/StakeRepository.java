package novel.server.stake;

import novel.server.novel.Novel;
import novel.server.writer.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StakeRepository extends JpaRepository<Stake, Long> {
    Optional<Stake> findStakeById(Long id);

    Optional<List<Stake>> findStakesByWriter(Writer writer);

    Optional<List<Stake>> findStakesByNovel(Novel novel);
}
