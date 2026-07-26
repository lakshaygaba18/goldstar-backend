package goldstar_backend.controller;

import goldstar_backend.dto.WorkerLoginRequest;
import goldstar_backend.dto.WorkerLoginResponse;
import goldstar_backend.service.WorkerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerAuthController {

    private final WorkerAuthService workerAuthService;

    @PostMapping("/login")
    public ResponseEntity<WorkerLoginResponse> login(
            @RequestBody WorkerLoginRequest request) {

        return ResponseEntity.ok(
                workerAuthService.login(request)
        );
    }
}