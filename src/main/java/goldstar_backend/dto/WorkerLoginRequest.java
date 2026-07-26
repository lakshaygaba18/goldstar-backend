package goldstar_backend.dto;

import lombok.Data;

@Data
public class WorkerLoginRequest {

    private String workerCode;
    private String pin;

}