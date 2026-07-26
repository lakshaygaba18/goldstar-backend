package goldstar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TryOnRequest {

    private Long customerId;

    private Long garmentId;

    private String productCode;

    private String customerImage;

    private Long outfitId;



}
