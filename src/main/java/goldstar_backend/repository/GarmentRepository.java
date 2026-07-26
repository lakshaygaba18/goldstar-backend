package goldstar_backend.repository;

import goldstar_backend.entity.Garment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GarmentRepository extends JpaRepository<Garment, Long> {

    long countByOwnerId(Long ownerId);

    List<Garment> findByNameContainingIgnoreCase(String name);

    List<Garment> findByCategoryIgnoreCase(String category);

    List<Garment> findByOwnerId(Long ownerId);

    List<Garment> findByOwnerIdAndCategoryIgnoreCase(Long ownerId, String category);

    Optional<Garment> findByGarmentCode(String garmentCode);

}