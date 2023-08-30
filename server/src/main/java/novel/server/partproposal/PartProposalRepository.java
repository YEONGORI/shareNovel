package novel.server.partproposal;

import novel.server.novel.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartProposalRepository extends JpaRepository<PartProposal, Long> {
    Optional<PartProposal> findNovelSectionById(Long id);

    Optional<List<PartProposal>> findNovelSectionsByNovel(Novel novel);

    Optional<PartProposal> findPartProposalById(Long id);
}
