package goldstar_backend.controller;

import com.google.zxing.WriterException;
import goldstar_backend.entity.TryOnJob;
import goldstar_backend.repository.TryOnJobRepository;
import goldstar_backend.service.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import goldstar_backend.service.TryOnService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PublicViewController {

    private final QRCodeService qrCodeService;
    private final TryOnJobRepository tryOnJobRepository;
    private final TryOnService tryOnService;

    @GetMapping(
            value = "/api/tryon/{jobId}/qrcode",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    @ResponseBody
    public ResponseEntity<byte[]> getQrCode(
            @PathVariable Long jobId
    ) throws WriterException, IOException {

        TryOnJob job = tryOnJobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        String viewUrl =
                "http://192.168.29.164:8080/view/" + job.getSessionCode();

        byte[] qrImage = qrCodeService.generateQRCode(viewUrl, 300, 300);

        return ResponseEntity.ok(qrImage);
    }

    @GetMapping("/view/{sessionCode}")
    public String viewResult(
            @PathVariable String sessionCode,
            Model model
    ) {

        TryOnJob job = tryOnJobRepository.findBySessionCode(sessionCode)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        model.addAttribute("garmentName", job.getGarment().getName());
        model.addAttribute("status", job.getStatus());
        model.addAttribute("resultImageUrl", job.getResultImageUrl());
        model.addAttribute("modelProvider", job.getModelProvider());

        return "tryon-result";
    }
    @GetMapping("/view/{sessionCode}/download")
    public ResponseEntity<String> downloadImage(
            @PathVariable String sessionCode
    ) {

        TryOnJob job = tryOnJobRepository.findBySessionCode(sessionCode)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        return ResponseEntity.ok(job.getResultImageUrl());
    }
    @PostMapping("/view/{sessionCode}/approve")
    @ResponseBody
    public ResponseEntity<String> approveTryOn(
            @PathVariable String sessionCode
    ) {

        tryOnService.approveTryOn(sessionCode);

        return ResponseEntity.ok("Approved");
    }

}