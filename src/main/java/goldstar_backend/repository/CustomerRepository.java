package goldstar_backend.repository;

import goldstar_backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    long countByOwnerId(Long ownerId);
}