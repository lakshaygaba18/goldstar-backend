package goldstar_backend.service;

import goldstar_backend.dto.GarmentRequest;
import goldstar_backend.dto.GarmentResponse;
import goldstar_backend.entity.Garment;
import goldstar_backend.entity.Owner;
import goldstar_backend.repository.GarmentRepository;
import goldstar_backend.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import goldstar_backend.entity.Worker;
import goldstar_backend.repository.WorkerRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GarmentService {

    private final GarmentRepository garmentRepository;
    private final OwnerRepository ownerRepository;
    private final WorkerRepository workerRepository;

    // CREATE GARMENT
    public GarmentResponse createGarment(GarmentRequest request, Long ownerId) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        String garmentCode = "GAR-" +
                String.format("%04d", garmentRepository.count() + 1);

        Garment garment = Garment.builder()
                .garmentCode(garmentCode)
                .name(request.getGarmentName())
                .category(request.getCategory())
                .size(request.getSize())
                .price(request.getPrice())
                .active(true)
                .stockQuantity(request.getStockQuantity())
                .owner(owner)
                .build();

        garment = garmentRepository.save(garment);

        return mapToResponse(garment);
    }

    // UPLOAD IMAGE
    public GarmentResponse uploadImage(Long garmentId,
                                       MultipartFile file) throws IOException {

        Garment garment = garmentRepository.findById(garmentId)
                .orElseThrow(() -> new RuntimeException("Garment not found"));

        String uploadDir = "uploads/";

        Files.createDirectories(Paths.get(uploadDir));

        String fileName =
                System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir, fileName);

        Files.write(filePath, file.getBytes());

        garment.setPrimaryImage(fileName);

        garment = garmentRepository.save(garment);

        return mapToResponse(garment);
    }

    // GET IMAGE
    public byte[] getGarmentImage(Long garmentId) throws IOException {

        Garment garment = garmentRepository.findById(garmentId)
                .orElseThrow(() -> new RuntimeException("Garment not found"));

        if (garment.getPrimaryImage() == null) {
            throw new RuntimeException("Image not found");
        }

        Path imagePath = Paths.get("uploads", garment.getPrimaryImage());

        return Files.readAllBytes(imagePath);
    }

    // SEARCH
    public List<GarmentResponse> search(String keyword) {

        return garmentRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // OWNER GARMENTS
    public List<GarmentResponse> getByOwner(Long ownerId) {

        return garmentRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    // WORKER GARMENTS
    public List<GarmentResponse> getByWorker(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Owner owner = worker.getOwner();

        return garmentRepository.findByOwnerId(owner.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // CATEGORY
    public List<GarmentResponse> getByCategory(String category) {

        return garmentRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // OWNER + CATEGORY
    public List<GarmentResponse> getByOwnerAndCategory(Long ownerId,
                                                       String category) {

        return garmentRepository.findByOwnerIdAndCategoryIgnoreCase(ownerId, category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // PAGINATION
    public Page<GarmentResponse> getAll(int page,
                                        int size,
                                        String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).ascending()
        );

        return garmentRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // UPDATE
    public GarmentResponse updateGarment(Long garmentId,
                                         GarmentRequest request) {

        Garment garment = garmentRepository.findById(garmentId)
                .orElseThrow(() -> new RuntimeException("Garment not found"));

        garment.setName(request.getGarmentName());
        garment.setCategory(request.getCategory());
        garment.setSize(request.getSize());
        garment.setPrice(request.getPrice());
        garment.setStockQuantity(request.getStockQuantity());

        garment = garmentRepository.save(garment);

        return mapToResponse(garment);
    }

    // DELETE
    public void deleteGarment(Long garmentId) {

        Garment garment = garmentRepository.findById(garmentId)
                .orElseThrow(() -> new RuntimeException("Garment not found"));

        if (garment.getPrimaryImage() != null) {

            try {

                Path imagePath = Paths.get(
                        "uploads",
                        garment.getPrimaryImage()
                );

                Files.deleteIfExists(imagePath);

            } catch (IOException ignored) {
            }
        }

        garmentRepository.delete(garment);
    }

    // MAPPER
    private GarmentResponse mapToResponse(Garment g) {

        return GarmentResponse.builder()
                .id(g.getId())
                .garmentCode(g.getGarmentCode())
                .name(g.getName())
                .category(g.getCategory())
                .size(g.getSize())
                .price(g.getPrice())
                .primaryImage(g.getPrimaryImage())
                .active(g.isActive())
                .stockQuantity(g.getStockQuantity())
                .build();
    }

}