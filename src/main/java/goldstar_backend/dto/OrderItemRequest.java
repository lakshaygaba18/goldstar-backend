package goldstar_backend.dto;

import lombok.Data;

@Data
public class OrderItemRequest {

    private Long garmentId;

    private Double price; // manually entered price (with discount if any)

}