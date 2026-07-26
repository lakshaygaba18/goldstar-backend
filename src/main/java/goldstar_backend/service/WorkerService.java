package goldstar_backend.service;

import goldstar_backend.dto.WorkerRequest;
import goldstar_backend.dto.WorkerResponse;
import goldstar_backend.entity.Owner;
import goldstar_backend.entity.Worker;
import goldstar_backend.repository.OwnerRepository;
import goldstar_backend.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final OwnerRepository ownerRepository;

    public WorkerResponse createWorker(WorkerRequest request, Long ownerId) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        String workerCode = "WRK-" + String.format("%04d", workerRepository.count() + 1);

        Worker worker = Worker.builder()
                .name(request.getName())
                .pin(request.getPin())
                .workerCode(workerCode)
                .active(true)
                .owner(owner)
                .build();

        Worker savedWorker = workerRepository.save(worker);

        return WorkerResponse.builder()
                .id(savedWorker.getId())
                .name(savedWorker.getName())
                .workerCode(savedWorker.getWorkerCode())
                .active(savedWorker.isActive())
                .build();
    }
    public void setActiveStatus(Long workerId, boolean active) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
        worker.setActive(active);
        workerRepository.save(worker);
    }
    public List<WorkerResponse> getWorkersByOwner(Long ownerId) {
        return workerRepository.findByOwnerId(ownerId)
                .stream()
                .map(w -> new WorkerResponse(w.getId(), w.getName(), w.getWorkerCode(), w.isActive()))
                .toList();
    }
}