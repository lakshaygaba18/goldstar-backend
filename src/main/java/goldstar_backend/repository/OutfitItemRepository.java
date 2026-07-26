package goldstar_backend.repository;

import goldstar_backend.entity.OutfitItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutfitItemRepository extends JpaRepository<OutfitItem, Long> {
}