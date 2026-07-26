package goldstar_backend.controller;

import goldstar_backend.dto.TryOnHistoryResponse;
import goldstar_backend.dto.TryOnJobRequest;
import goldstar_backend.dto.TryOnJobResponse;
import goldstar_backend.entity.TryOnStatus;
import goldstar_backend.service.TryOnService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tryon")
@RequiredArgsConstructor
public class TryOnController {

    private final TryOnService tryOnService;

    @PostMapping
    public TryOnJobResponse createJob(@RequestBody TryOnJobRequest request) {
        return tryOnService.createJob(request);
    }

    @GetMapping("/owner/{ownerId}/history")
    public List<TryOnHistoryResponse> getOwnerHistory(
            @PathVariable Long ownerId,
            @RequestParam(required = false) TryOnStatus status
    ) {

        if (status == null) {
            return tryOnService.getOwnerHistory(ownerId);
        }

        return tryOnService.getOwnerHistory(ownerId, status);
    }

    @GetMapping("/worker/{workerId}/history")
    public List<TryOnHistoryResponse> getWorkerHistory(
            @PathVariable Long workerId
    ) {
        return tryOnService.getWorkerHistory(workerId);
    }
}