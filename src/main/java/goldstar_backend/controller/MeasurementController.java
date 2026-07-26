package goldstar_backend.controller;

import goldstar_backend.dto.MeasurementRequest;
import goldstar_backend.dto.MeasurementResponse;
import goldstar_backend.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @PostMapping("/{customerId}/measurements")
    public ResponseEntity<MeasurementResponse> saveMeasurement(
            @PathVariable Long customerId,
            @RequestBody MeasurementRequest request) {

        return ResponseEntity.ok(
                measurementService.saveMeasurement(customerId, request)
        );
    }

    @GetMapping("/{customerId}/measurements")
    public ResponseEntity<MeasurementResponse> getMeasurement(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                measurementService.getMeasurement(customerId)
        );
    }

    @PutMapping("/{customerId}/measurements")
    public ResponseEntity<MeasurementResponse> updateMeasurement(
            @PathVariable Long customerId,
            @RequestBody MeasurementRequest request) {

        return ResponseEntity.ok(
                measurementService.updateMeasurement(customerId, request)
        );
    }
}