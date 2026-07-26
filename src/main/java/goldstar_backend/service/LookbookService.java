package goldstar_backend.service;

import goldstar_backend.dto.LookbookRequest;
import goldstar_backend.dto.LookbookResponse;
import goldstar_backend.entity.Customer;
import goldstar_backend.entity.Lookbook;
import goldstar_backend.entity.Outfit;
import goldstar_backend.repository.CustomerRepository;
import goldstar_backend.repository.LookbookRepository;
import goldstar_backend.repository.OutfitRepository;
import goldstar_backend.util.PdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LookbookService {

    private final LookbookRepository lookbookRepository;
    private final CustomerRepository customerRepository;
    private final OutfitRepository outfitRepository;

    public LookbookResponse createLookbook(LookbookRequest request) throws IOException {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Outfit outfit = outfitRepository.findById(request.getOutfitId())
                .orElseThrow(() -> new RuntimeException("Outfit not found"));

        String pdfPath = PdfGenerator.generateLookbook(
                request.getTitle(),
                customer.getName(),
                outfit.getOutfitName()
        );

        Lookbook lookbook = Lookbook.builder()
                .title(request.getTitle())
                .pdfPath(pdfPath)
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .outfit(outfit)
                .build();

        lookbook = lookbookRepository.save(lookbook);

        return LookbookResponse.builder()
                .id(lookbook.getId())
                .title(lookbook.getTitle())
                .pdfPath(lookbook.getPdfPath())
                .build();
    }

    public List<LookbookResponse> getCustomerLookbooks(Long customerId) {

        return lookbookRepository.findByCustomerId(customerId)
                .stream()
                .map(l -> LookbookResponse.builder()
                        .id(l.getId())
                        .title(l.getTitle())
                        .pdfPath(l.getPdfPath())
                        .build())
                .toList();
    }

    public LookbookResponse getLookbook(Long id) {

        Lookbook lookbook = lookbookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lookbook not found"));

        return LookbookResponse.builder()
                .id(lookbook.getId())
                .title(lookbook.getTitle())
                .pdfPath(lookbook.getPdfPath())
                .build();
    }

    public void deleteLookbook(Long id) {

        Lookbook lookbook = lookbookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lookbook not found"));

        lookbookRepository.delete(lookbook);
    }

    public byte[] downloadLookbook(Long id) throws IOException {

        Lookbook lookbook = lookbookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lookbook not found"));

        Path path = Paths.get(lookbook.getPdfPath());

        if (!Files.exists(path)) {
            throw new RuntimeException("PDF not found");
        }

        return Files.readAllBytes(path);
    }
}