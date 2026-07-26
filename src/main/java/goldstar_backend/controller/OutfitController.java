package goldstar_backend.controller;

import goldstar_backend.dto.OutfitRequest;
import goldstar_backend.dto.OutfitResponse;
import goldstar_backend.service.OutfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/outfits")
@RequiredArgsConstructor
public class OutfitController {

    private final OutfitService outfitService;

    @PostMapping("/{customerId}")
    public ResponseEntity<OutfitResponse> createOutfit(
            @PathVariable Long customerId,
            @RequestBody OutfitRequest request){

        return ResponseEntity.ok(
                outfitService.createOutfit(customerId, request)
        );
    }
}