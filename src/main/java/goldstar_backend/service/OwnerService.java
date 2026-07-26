package goldstar_backend.service;

import goldstar_backend.dto.OwnerRegisterRequest;
import goldstar_backend.entity.Owner;
import goldstar_backend.entity.Subscription; // NEW
import goldstar_backend.repository.OwnerRepository;
import goldstar_backend.repository.SubscriptionRepository; // NEW
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime; // NEW

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final SubscriptionRepository subscriptionRepository; // NEW

    public Owner registerOwner(OwnerRegisterRequest request) {

        System.out.println("Register API called");

        if (ownerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Owner owner = Owner.builder()
                .shopName(request.getShopName())
                .ownerName(request.getOwnerName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .phone(request.getPhone())
                .build();

        Owner saved = ownerRepository.save(owner);

        System.out.println("Saved Owner ID = " + saved.getId());

        // NEW — create a default (inactive/empty) subscription for this owner
        Subscription defaultSub = Subscription.builder()
                .owner(saved)
                .totalTokens(0)
                .tokensRemaining(0)
                .planName("No active plan")
                .active(false)
                .purchasedAt(LocalDateTime.now())
                .build();

        subscriptionRepository.save(defaultSub);

        System.out.println("Default subscription created for owner ID = " + saved.getId());

        return saved;
    }

    public Owner uploadLogo(Long ownerId,
                            MultipartFile file) throws IOException {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        String uploadDir = "uploads/logo/";

        Files.createDirectories(Paths.get(uploadDir));

        String fileName = System.currentTimeMillis()
                + "_"
                + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir, fileName);

        Files.write(filePath, file.getBytes());

        owner.setLogo(fileName);

        return ownerRepository.save(owner);
    }

    public byte[] getLogo(Long ownerId) throws IOException {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (owner.getLogo() == null) {
            throw new RuntimeException("Logo not uploaded");
        }

        Path path = Paths.get("uploads/logo", owner.getLogo());

        return Files.readAllBytes(path);
    }
}