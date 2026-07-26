package goldstar_backend.repository;

import goldstar_backend.entity.TryOnJob;
import goldstar_backend.entity.TryOnStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TryOnJobRepository extends JpaRepository<TryOnJob, Long> {

    Optional<TryOnJob> findBySessionCode(String sessionCode);

    List<TryOnJob> findTop5ByWorkerOwnerIdOrderByCreatedAtDesc(Long ownerId);
    List<TryOnJob> findByWorkerOwnerIdOrderByCreatedAtDesc(Long ownerId);

    List<TryOnJob> findByWorkerIdOrderByCreatedAtDesc(Long workerId);

    long countByWorkerOwnerId(Long ownerId);

    long countByWorkerOwnerIdAndStatus(Long ownerId, TryOnStatus status);

    long countByWorkerId(Long workerId);

    @Query("""
            SELECT COUNT(t)
            FROM TryOnJob t
            WHERE t.worker.id = :workerId
            AND t.createdAt >= :startOfDay
            AND t.createdAt < :endOfDay
            """)
    long countByWorkerIdAndDateRange(
            @Param("workerId") Long workerId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("""
            SELECT COUNT(t)
            FROM TryOnJob t
            WHERE t.worker.owner.id = :ownerId
            AND t.createdAt >= :startOfDay
            AND t.createdAt < :endOfDay
            """)
    long countByOwnerIdAndDateRange(
            @Param("ownerId") Long ownerId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("""
            SELECT DISTINCT t.worker.id
            FROM TryOnJob t
            WHERE t.worker.owner.id = :ownerId
            AND t.createdAt >= :startOfDay
            AND t.createdAt < :endOfDay
            """)
    List<Long> findDistinctWorkerIdsByOwnerIdAndDateRange(
            @Param("ownerId") Long ownerId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
    List<TryOnJob> findByWorkerOwnerIdAndStatusOrderByCreatedAtDesc(
            Long ownerId,
            TryOnStatus status
    );
}