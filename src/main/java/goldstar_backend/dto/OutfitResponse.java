package goldstar_backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutfitResponse {

    private Long id;

    private String outfitName;

    private boolean active;

    private Long customerId;

    private List<OutfitItemResponse> garments;
}