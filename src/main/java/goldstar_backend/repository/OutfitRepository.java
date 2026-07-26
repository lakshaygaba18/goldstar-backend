package goldstar_backend.repository;

import goldstar_backend.entity.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OutfitRepository extends JpaRepository<Outfit, Long> {
    long countByCustomerOwnerId(Long ownerId);
    List<Outfit> findByCustomerId(Long customerId);
}