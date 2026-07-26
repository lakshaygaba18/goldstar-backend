package goldstar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LookbookRequest {

    private Long customerId;

    private Long outfitId;

    private String title;

}