package goldstar_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkerLoginResponse {

    private Long workerId;
    private String name;
    private String token;

}