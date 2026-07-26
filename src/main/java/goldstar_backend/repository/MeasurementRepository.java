package goldstar_backend.repository;

import goldstar_backend.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    Optional<Measurement> findByCustomerId(Long customerId);

}