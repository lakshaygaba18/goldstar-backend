package goldstar_backend.repository;

import goldstar_backend.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByWorkerCode(String workerCode);

    List<Worker> findByOwnerId(Long ownerId); // NEW

}