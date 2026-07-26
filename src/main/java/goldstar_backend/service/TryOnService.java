package goldstar_backend.service;

import goldstar_backend.dto.TryOnHistoryResponse;
import goldstar_backend.dto.TryOnJobRequest;
import goldstar_backend.dto.TryOnJobResponse;
import goldstar_backend.entity.Garment;
import goldstar_backend.entity.TryOnJob;
import goldstar_backend.entity.TryOnStatus;
import goldstar_backend.entity.Worker;
import goldstar_backend.repository.GarmentRepository;
import goldstar_backend.repository.TryOnJobRepository;
import goldstar_backend.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TryOnService {

    private final TryOnJobRepository tryOnJobRepository;
    private final WorkerRepository workerRepository;
    private final GarmentRepository garmentRepository;
    private final SubscriptionService subscriptionService;

    public TryOnJobResponse createJob(TryOnJobRequest request) {

        Worker worker = workerRepository.findById(request.getWorkerId())
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Garment garment = garmentRepository.findByGarmentCode(request.getGarmentCode())
                .orElseThrow(() -> new RuntimeException("Garment not found"));

        Long ownerId = garment.getOwner().getId();

        if (!subscriptionService.hasTokensAvailable(ownerId)) {
            throw new RuntimeException("Tokens exhausted. Please renew subscription.");
        }

        TryOnJob job = TryOnJob.builder()
                .worker(worker)
                .garment(garment)
                .sessionCode(UUID.randomUUID().toString())
                .status(TryOnStatus.PROCESSING)
                .avatarUrl(null)
                .resultImageUrl(null)
                .modelProvider(null)
                .tokensUsed(1)
                .customerApproved(false)
                .createdAt(LocalDateTime.now())
                .build();

        job = tryOnJobRepository.save(job);

        // AI integration will come later

        job.setStatus(TryOnStatus.COMPLETED);
        job.setCompletedAt(LocalDateTime.now());
        job.setAvatarUrl(
                "https://dummy.goldstar.ai/avatar_" + job.getId() + ".glb"
        );
        job.setResultImageUrl(
                "https://dummy.goldstar.ai/result_" + job.getId() + ".png"
        );
        job.setModelProvider("RODIN");

        job = tryOnJobRepository.save(job);

        subscriptionService.deductToken(ownerId);

        return TryOnJobResponse.builder()
                .jobId(job.getId())
                .sessionCode(job.getSessionCode())
                .status(job.getStatus())
                .garmentName(job.getGarment().getName())
                .avatarUrl(job.getAvatarUrl())
                .resultImageUrl(job.getResultImageUrl())
                .modelProvider(job.getModelProvider())
                .tokensUsed(job.getTokensUsed())
                .customerApproved(job.getCustomerApproved())
                .createdAt(job.getCreatedAt())
                .completedAt(job.getCompletedAt())
                .build();
    }

    // Owner History (All)
    public List<TryOnHistoryResponse> getOwnerHistory(Long ownerId) {

        return tryOnJobRepository
                .findByWorkerOwnerIdOrderByCreatedAtDesc(ownerId)
                .stream()
                .map(job -> TryOnHistoryResponse.builder()
                        .id(job.getId())
                        .garmentName(job.getGarment().getName())
                        .garmentCode(job.getGarment().getGarmentCode())
                        .status(job.getStatus().name())
                        .generatedImage(job.getResultImageUrl())
                        .createdAt(job.getCreatedAt())
                        .build())
                .toList();
    }

    // Owner History (Status Filter)
    public List<TryOnHistoryResponse> getOwnerHistory(
            Long ownerId,
            TryOnStatus status
    ) {

        return tryOnJobRepository
                .findByWorkerOwnerIdAndStatusOrderByCreatedAtDesc(ownerId, status)
                .stream()
                .map(job -> TryOnHistoryResponse.builder()
                        .id(job.getId())
                        .garmentName(job.getGarment().getName())
                        .garmentCode(job.getGarment().getGarmentCode())
                        .status(job.getStatus().name())
                        .generatedImage(job.getResultImageUrl())
                        .createdAt(job.getCreatedAt())
                        .build())
                .toList();
    }

    // Worker History
    public List<TryOnHistoryResponse> getWorkerHistory(Long workerId) {

        return tryOnJobRepository
                .findByWorkerIdOrderByCreatedAtDesc(workerId)
                .stream()
                .map(job -> TryOnHistoryResponse.builder()
                        .id(job.getId())
                        .garmentName(job.getGarment().getName())
                        .garmentCode(job.getGarment().getGarmentCode())
                        .status(job.getStatus().name())
                        .generatedImage(job.getResultImageUrl())
                        .createdAt(job.getCreatedAt())
                        .build())
                .toList();
    }
    public void approveTryOn(String sessionCode) {

        TryOnJob job = tryOnJobRepository.findBySessionCode(sessionCode)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        job.setCustomerApproved(true);

        tryOnJobRepository.save(job);
    }
}