package goldstar_backend.service;

import goldstar_backend.dto.OutfitItemResponse;
import goldstar_backend.dto.OutfitRequest;
import goldstar_backend.dto.OutfitResponse;
import goldstar_backend.entity.Customer;
import goldstar_backend.entity.Garment;
import goldstar_backend.entity.Outfit;
import goldstar_backend.entity.OutfitItem;
import goldstar_backend.repository.CustomerRepository;
import goldstar_backend.repository.GarmentRepository;
import goldstar_backend.repository.OutfitItemRepository;
import goldstar_backend.repository.OutfitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutfitService {

    private final OutfitRepository outfitRepository;
    private final OutfitItemRepository outfitItemRepository;
    private final CustomerRepository customerRepository;
    private final GarmentRepository garmentRepository;

    public OutfitResponse createOutfit(Long customerId, OutfitRequest request){

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Outfit outfit = Outfit.builder()
                .outfitName(request.getOutfitName())
                .active(true)
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .build();

        Outfit savedOutfit = outfitRepository.save(outfit);

        List<OutfitItemResponse> responses = new ArrayList<>();

        for(Long garmentId : request.getGarmentIds()){

            Garment garment = garmentRepository.findById(garmentId)
                    .orElseThrow(() -> new RuntimeException("Garment not found"));

            OutfitItem item = OutfitItem.builder()
                    .outfit(savedOutfit)
                    .garment(garment)
                    .build();

            outfitItemRepository.save(item);

            responses.add(
                    OutfitItemResponse.builder()
                            .garmentId(garment.getId())
                            .garmentCode(garment.getGarmentCode())
                            .garmentName(garment.getName())
                            .category(garment.getCategory())
                            .primaryImage(garment.getPrimaryImage())
                            .build()
            );
        }

        return OutfitResponse.builder()
                .id(savedOutfit.getId())
                .outfitName(savedOutfit.getOutfitName())
                .active(savedOutfit.isActive())
                .customerId(customer.getId())
                .garments(responses)
                .build();
    }
}