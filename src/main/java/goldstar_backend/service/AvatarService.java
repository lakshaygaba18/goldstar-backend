package goldstar_backend.service;

import goldstar_backend.dto.AvatarRequest;
import goldstar_backend.dto.AvatarResponse;
import goldstar_backend.entity.Avatar;
import goldstar_backend.entity.Customer;
import goldstar_backend.repository.AvatarRepository;
import goldstar_backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final CustomerRepository customerRepository;

    public AvatarResponse createAvatar(AvatarRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Avatar avatar = Avatar.builder()
                .avatarId("AVT-" + System.currentTimeMillis())
                .status("PENDING")
                .previewImageUrl(null)
                .customer(customer)
                .build();

        Avatar savedAvatar = avatarRepository.save(avatar);

        return AvatarResponse.builder()
                .id(savedAvatar.getId())
                .avatarId(savedAvatar.getAvatarId())
                .status(savedAvatar.getStatus())
                .previewImageUrl(savedAvatar.getPreviewImageUrl())
                .build();
    }
}