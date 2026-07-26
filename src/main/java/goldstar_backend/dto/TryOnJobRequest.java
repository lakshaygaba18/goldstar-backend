package goldstar_backend.dto;

import lombok.Data;

@Data
public class TryOnJobRequest {

    private Long workerId;

    private String garmentCode;

}