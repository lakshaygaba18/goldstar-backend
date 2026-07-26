package goldstar_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GarmentResponse {

    private Long id;

    private String garmentCode;

    private String name;

    private String category;

    private String size;

    private Double price;

    private String primaryImage;

    private boolean active;

    private Integer stockQuantity;

}