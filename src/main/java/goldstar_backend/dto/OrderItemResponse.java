package goldstar_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

    private Long garmentId;

    private String garmentName;

    private Double price;

}