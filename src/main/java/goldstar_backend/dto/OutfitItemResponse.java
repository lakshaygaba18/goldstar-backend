package goldstar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutfitItemResponse {

    private Long garmentId;

    private String garmentCode;

    private String garmentName;

    private String category;

    private String primaryImage;
}