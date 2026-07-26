package goldstar_backend.repository;

import goldstar_backend.entity.TryOnResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TryOnResultRepository
        extends JpaRepository<TryOnResult,Long> {
}