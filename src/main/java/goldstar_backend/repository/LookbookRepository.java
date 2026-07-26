package goldstar_backend.repository;

import goldstar_backend.entity.Lookbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LookbookRepository extends JpaRepository<Lookbook, Long> {

    List<Lookbook> findByCustomerId(Long customerId);

}