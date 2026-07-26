package goldstar_backend.controller;

import goldstar_backend.dto.OwnerRegisterRequest;
import goldstar_backend.entity.Owner;
import goldstar_backend.repository.OwnerRepository;
import goldstar_backend.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerRepository ownerRepository;

    @PostMapping("/register")
    public ResponseEntity<Owner> registerOwner(
            @RequestBody OwnerRegisterRequest request) {

        return ResponseEntity.ok(
                ownerService.registerOwner(request)
        );
    }

    @PostMapping("/{ownerId}/logo")
    public ResponseEntity<Owner> uploadLogo(
            @PathVariable Long ownerId,
            @RequestParam("image") MultipartFile image) throws IOException {

        return ResponseEntity.ok(
                ownerService.uploadLogo(ownerId, image)
        );
    }

    @GetMapping("/{ownerId}/logo")
    public ResponseEntity<byte[]> getLogo(
            @PathVariable Long ownerId) throws IOException {

        byte[] logo = ownerService.getLogo(ownerId);

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(logo);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOwners() {

        return ResponseEntity.ok(
                ownerRepository.findAll()
        );
    }

    @GetMapping("/count")
    public long count() {

        return ownerRepository.count();
    }

}