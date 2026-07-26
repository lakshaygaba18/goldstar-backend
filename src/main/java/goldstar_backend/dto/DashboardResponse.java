package goldstar_backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private long totalGarments;

    private long totalCustomers;

    private long totalTryOns;

    private long pendingTryOns;

    private List<TryOnHistoryResponse> recentTryOns;

    private long todayTryOns;

    private Integer tokensRemaining;

    private List<WorkerStatsResponse> workerStats;

    private Double todaySales; // NEW

}