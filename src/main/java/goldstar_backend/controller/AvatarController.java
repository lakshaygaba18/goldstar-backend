package goldstar_backend.controller;

import goldstar_backend.dto.AvatarRequest;
import goldstar_backend.dto.AvatarResponse;
import goldstar_backend.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @PostMapping
    public ResponseEntity<AvatarResponse> createAvatar(
            @RequestBody AvatarRequest request) {

        return ResponseEntity.ok(
                avatarService.createAvatar(request)
        );
    }
}