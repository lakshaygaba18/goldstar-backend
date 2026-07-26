package goldstar_backend.service;

import goldstar_backend.repository.WorkerRepository;
import goldstar_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import goldstar_backend.dto.WorkerLoginRequest;
import goldstar_backend.dto.WorkerLoginResponse;
import goldstar_backend.entity.Worker;

@Service
@RequiredArgsConstructor
public class WorkerAuthService {

    private final WorkerRepository workerRepository;
    private final JwtService jwtService;

    public WorkerLoginResponse login(WorkerLoginRequest request) {

        Worker worker = workerRepository.findByWorkerCode(request.getWorkerCode())
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (!worker.isActive()) {
            throw new RuntimeException("Access disabled by shop owner");
        }

        if (!worker.getPin().equals(request.getPin())) {
            throw new RuntimeException("Invalid PIN");
        }

        String token = jwtService.generateWorkerToken(worker.getWorkerCode());

        return new WorkerLoginResponse(
                worker.getId(),
                worker.getName(),
                token
        );
    }
}