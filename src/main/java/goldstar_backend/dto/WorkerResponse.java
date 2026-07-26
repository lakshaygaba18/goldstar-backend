package goldstar_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerResponse {

    private Long id;

    private String name;

    private String workerCode;

    private boolean active;


}