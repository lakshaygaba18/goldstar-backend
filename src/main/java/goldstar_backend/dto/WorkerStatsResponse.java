package goldstar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerStatsResponse {

    private Long workerId;

    private String workerName;

    private long todayGenerations;

    private long totalGenerations;

}