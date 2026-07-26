package goldstar_backend.controller;

import goldstar_backend.dto.GarmentRequest;
import goldstar_backend.dto.GarmentResponse;
import goldstar_backend.service.GarmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/garments")
@RequiredArgsConstructor
public class GarmentController {

    private final GarmentService garmentService;

    @PostMapping("/{ownerId}")
    public ResponseEntity<GarmentResponse> createGarment(
            @PathVariable Long ownerId,
            @RequestBody GarmentRequest request) {

        return ResponseEntity.ok(
                garmentService.createGarment(request, ownerId)
        );
    }

    @PostMapping("/{garmentId}/upload")
    public ResponseEntity<GarmentResponse> uploadImage(
            @PathVariable Long garmentId,
            @RequestParam("image") MultipartFile image) throws IOException {

        return ResponseEntity.ok(
                garmentService.uploadImage(garmentId, image)
        );
    }

    @GetMapping("/{garmentId}/image")
    public ResponseEntity<byte[]> getGarmentImage(
            @PathVariable Long garmentId) throws IOException {

        byte[] image = garmentService.getGarmentImage(garmentId);

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(image);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GarmentResponse>> search(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                garmentService.search(keyword)
        );
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<GarmentResponse>> getByOwner(
            @PathVariable Long ownerId) {

        return ResponseEntity.ok(
                garmentService.getByOwner(ownerId)
        );
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<GarmentResponse>> getByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(
                garmentService.getByCategory(category)
        );
    }

    @GetMapping("/owner/{ownerId}/category/{category}")
    public ResponseEntity<List<GarmentResponse>> getByOwnerAndCategory(
            @PathVariable Long ownerId,
            @PathVariable String category) {

        return ResponseEntity.ok(
                garmentService.getByOwnerAndCategory(ownerId, category)
        );
    }

    @GetMapping
    public ResponseEntity<Page<GarmentResponse>> getAll(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return ResponseEntity.ok(
                garmentService.getAll(page, size, sortBy)
        );
    }

    @PutMapping("/{garmentId}")
    public ResponseEntity<GarmentResponse> updateGarment(
            @PathVariable Long garmentId,
            @RequestBody GarmentRequest request) {

        return ResponseEntity.ok(
                garmentService.updateGarment(garmentId, request)
        );
    }

    @DeleteMapping("/{garmentId}")
    public ResponseEntity<Void> deleteGarment(
            @PathVariable Long garmentId) {

        garmentService.deleteGarment(garmentId);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<GarmentResponse>> getByWorker(
            @PathVariable Long workerId) {

        return ResponseEntity.ok(
                garmentService.getByWorker(workerId)
        );
    }
}