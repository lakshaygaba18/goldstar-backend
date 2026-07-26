package goldstar_backend.controller;

import goldstar_backend.dto.CustomerPhotoResponse;
import goldstar_backend.service.CustomerPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerPhotoController {

    private final CustomerPhotoService customerPhotoService;

    @PostMapping("/{customerId}/photos")
    public ResponseEntity<CustomerPhotoResponse> uploadPhoto(

            @PathVariable Long customerId,

            @RequestParam("image") MultipartFile image,

            @RequestParam("photoType") String photoType

    ) throws IOException {

        return ResponseEntity.ok(
                customerPhotoService.uploadPhoto(
                        customerId,
                        image,
                        photoType
                )
        );
    }
    @GetMapping("/{photoId}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long photoId){

        byte[] image = customerPhotoService.getPhoto(photoId);

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(image);
    }

}