package goldstar_backend.controller;

import goldstar_backend.dto.WorkerRequest;
import goldstar_backend.dto.WorkerResponse;
import goldstar_backend.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @PostMapping("/{ownerId}")
    public ResponseEntity<WorkerResponse> createWorker(
            @PathVariable Long ownerId,
            @RequestBody WorkerRequest request
    )
    {

        return ResponseEntity.ok(
                workerService.createWorker(request, ownerId)
        );

    }
    @PatchMapping("/{workerId}/toggle-access")
    public ResponseEntity<String> toggleAccess(
            @PathVariable Long workerId,
            @RequestParam boolean active) {

        workerService.setActiveStatus(workerId, active);
        return ResponseEntity.ok(active ? "Access enabled" : "Access disabled");
    }
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<WorkerResponse>> getWorkersByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(workerService.getWorkersByOwner(ownerId));
    }

}