package novel.server.writer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WriterRepository extends JpaRepository<Writer, Long> {
    Optional<Writer> findWriterByPenName(String penName);
}
