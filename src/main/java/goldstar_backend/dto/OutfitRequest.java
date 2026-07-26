package goldstar_backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutfitRequest {

    private String outfitName;

    private List<Long> garmentIds;
}