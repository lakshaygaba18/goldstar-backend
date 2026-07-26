package goldstar_backend.controller;

import goldstar_backend.dto.LookbookRequest;
import goldstar_backend.dto.LookbookResponse;
import goldstar_backend.service.LookbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/lookbooks")
@RequiredArgsConstructor
public class LookbookController {

    private final LookbookService lookbookService;

    @PostMapping
    public ResponseEntity<LookbookResponse> createLookbook(
            @RequestBody LookbookRequest request) throws IOException {

        return ResponseEntity.ok(
                lookbookService.createLookbook(request)
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LookbookResponse>> getCustomerLookbooks(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                lookbookService.getCustomerLookbooks(customerId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LookbookResponse> getLookbook(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                lookbookService.getLookbook(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLookbook(
            @PathVariable Long id) {

        lookbookService.deleteLookbook(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadLookbook(
            @PathVariable Long id) throws IOException {

        byte[] pdf = lookbookService.downloadLookbook(id);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }
}