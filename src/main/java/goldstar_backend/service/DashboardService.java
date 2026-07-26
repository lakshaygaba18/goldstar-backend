package goldstar_backend.service;

import goldstar_backend.dto.DashboardResponse;
import goldstar_backend.dto.TryOnHistoryResponse;
import goldstar_backend.dto.WorkerStatsResponse;
import goldstar_backend.entity.Subscription;
import goldstar_backend.entity.TryOnStatus;
import goldstar_backend.entity.Worker;
import goldstar_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final GarmentRepository garmentRepository;
    private final CustomerRepository customerRepository;
    private final TryOnJobRepository tryOnJobRepository;
    private final WorkerRepository workerRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final OrderRepository orderRepository;

    public DashboardResponse getDashboard(Long ownerId) {

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<TryOnHistoryResponse> history =
                tryOnJobRepository
                        .findTop5ByWorkerOwnerIdOrderByCreatedAtDesc(ownerId)
                        .stream()
                        .map(job -> TryOnHistoryResponse.builder()
                                .id(job.getId())
                                .status(job.getStatus().name())
                                .generatedImage(job.getResultImageUrl())
                                .createdAt(job.getCreatedAt())
                                .build())
                        .toList();

        long todayTryOns =
                tryOnJobRepository.countByOwnerIdAndDateRange(
                        ownerId,
                        startOfDay,
                        endOfDay
                );

        Integer tokensRemaining = subscriptionRepository.findByOwnerId(ownerId)
                .map(Subscription::getTokensRemaining)
                .orElse(0);

        List<Worker> workers = workerRepository.findByOwnerId(ownerId);

        List<WorkerStatsResponse> workerStats =
                workers.stream()
                        .map(worker -> WorkerStatsResponse.builder()
                                .workerId(worker.getId())
                                .workerName(worker.getName())
                                .todayGenerations(
                                        tryOnJobRepository.countByWorkerIdAndDateRange(
                                                worker.getId(),
                                                startOfDay,
                                                endOfDay
                                        )
                                )
                                .totalGenerations(
                                        tryOnJobRepository.countByWorkerId(worker.getId())
                                )
                                .build())
                        .toList();

        Double todaySales =
                orderRepository.sumTodaySalesByOwnerId(
                        ownerId,
                        startOfDay,
                        endOfDay
                );

        return DashboardResponse.builder()
                .totalGarments(
                        garmentRepository.countByOwnerId(ownerId)
                )
                .totalCustomers(
                        customerRepository.countByOwnerId(ownerId)
                )
                .totalTryOns(
                        tryOnJobRepository.countByWorkerOwnerId(ownerId)
                )
                .pendingTryOns(
                        tryOnJobRepository.countByWorkerOwnerIdAndStatus(
                                ownerId,
                                TryOnStatus.PROCESSING
                        )
                )
                .recentTryOns(history)
                .todayTryOns(todayTryOns)
                .tokensRemaining(tokensRemaining)
                .workerStats(workerStats)
                .todaySales(todaySales)
                .build();
    }
}