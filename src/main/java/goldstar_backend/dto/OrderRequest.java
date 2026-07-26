package goldstar_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Long customerId;

    private Long workerId; // optional — null if owner is confirming directly

    private List<OrderItemRequest> items;

}