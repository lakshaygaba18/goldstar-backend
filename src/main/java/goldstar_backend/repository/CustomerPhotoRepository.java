package goldstar_backend.repository;

import goldstar_backend.entity.Customer;
import goldstar_backend.entity.CustomerPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerPhotoRepository
        extends JpaRepository<CustomerPhoto, Long> {

    List<CustomerPhoto> findByCustomer(Customer customer);
    List<CustomerPhoto> findByCustomerId(Long customerId);

    // NEW — finds all photos uploaded before a given time (i.e. "old" photos)
    List<CustomerPhoto> findByUploadedAtBefore(LocalDateTime cutoff);
}