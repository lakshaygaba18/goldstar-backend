package goldstar_backend.repository;

import goldstar_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOwnerId(Long ownerId);

    // total sales today
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.owner.id = :ownerId " +
            "AND o.orderedAt >= :startOfDay AND o.orderedAt < :endOfDay")
    Double sumTodaySalesByOwnerId(@Param("ownerId") Long ownerId,
                                  @Param("startOfDay") LocalDateTime startOfDay,
                                  @Param("endOfDay") LocalDateTime endOfDay);

    // total sales all-time
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.owner.id = :ownerId")
    Double sumTotalSalesByOwnerId(@Param("ownerId") Long ownerId);

    // per-worker sales today (for "which worker sold the most" feature)
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.worker.id = :workerId " +
            "AND o.orderedAt >= :startOfDay AND o.orderedAt < :endOfDay")
    Double sumTodaySalesByWorkerId(@Param("workerId") Long workerId,
                                   @Param("startOfDay") LocalDateTime startOfDay,
                                   @Param("endOfDay") LocalDateTime endOfDay);
}