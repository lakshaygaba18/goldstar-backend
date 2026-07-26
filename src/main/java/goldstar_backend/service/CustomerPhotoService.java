package goldstar_backend.service;

import goldstar_backend.dto.CustomerPhotoResponse;
import goldstar_backend.entity.Customer;
import goldstar_backend.entity.CustomerPhoto;
import goldstar_backend.repository.CustomerPhotoRepository;
import goldstar_backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled; // NEW
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List; // NEW

@Service
@RequiredArgsConstructor
public class CustomerPhotoService {

    private final CustomerRepository customerRepository;
    private final CustomerPhotoRepository customerPhotoRepository;

    public CustomerPhotoResponse uploadPhoto(Long customerId,
                                             MultipartFile image,
                                             String photoType) throws IOException {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerPhoto photo = CustomerPhoto.builder()
                .imageName(image.getOriginalFilename())
                .photoType(photoType)
                .image(image.getBytes())
                .uploadedAt(LocalDateTime.now())
                .customer(customer)
                .build();

        CustomerPhoto saved = customerPhotoRepository.save(photo);

        return CustomerPhotoResponse.builder()
                .id(saved.getId())
                .imageName(saved.getImageName())
                .photoType(saved.getPhotoType())
                .build();
    }

    public byte[] getPhoto(Long photoId){

        CustomerPhoto photo = customerPhotoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));

        return photo.getImage();
    }

    // NEW — runs automatically every 60 seconds
    @Scheduled(fixedRate = 60000)
    public void deleteExpiredPhotos() {

        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(10); // photos older than 10 min

        List<CustomerPhoto> expiredPhotos =
                customerPhotoRepository.findByUploadedAtBefore(cutoff);

        if (!expiredPhotos.isEmpty()) {
            customerPhotoRepository.deleteAll(expiredPhotos);
            System.out.println("Deleted " + expiredPhotos.size() + " expired customer photos");
        }
    }
}