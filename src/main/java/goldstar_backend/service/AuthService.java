package goldstar_backend.service;

import goldstar_backend.dto.LoginRequest;
import goldstar_backend.dto.LoginResponse;
import goldstar_backend.entity.Owner;
import goldstar_backend.repository.OwnerRepository;
import goldstar_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import goldstar_backend.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final OwnerRepository ownerRepository;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        Owner owner = ownerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (!owner.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(owner.getEmail());

        return new LoginResponse(
                token,
                owner.getId(),
                owner.getShopName(),
                owner.getOwnerName()
        );
    }
    public void register(RegisterRequest request) {

        if (ownerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Owner owner = Owner.builder()
                .shopName(request.getShopName())
                .ownerName(request.getOwnerName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .build();

        ownerRepository.save(owner);
    }
}