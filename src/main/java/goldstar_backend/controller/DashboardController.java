package goldstar_backend.controller;

import goldstar_backend.dto.DashboardResponse;
import goldstar_backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{ownerId}")
    public ResponseEntity<DashboardResponse> getDashboard(
            @PathVariable Long ownerId){

        return ResponseEntity.ok(
                dashboardService.getDashboard(ownerId)
        );
    }
}