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
public class GarmentRequest {

    private String garmentName;

    private String category;

    private String size;

    private Double price;

    private Integer stockQuantity;

}