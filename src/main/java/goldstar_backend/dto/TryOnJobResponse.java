package goldstar_backend.dto;

import goldstar_backend.entity.TryOnStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TryOnJobResponse {

    private Long jobId;

    private String sessionCode;

    private TryOnStatus status;

    private String garmentName;

    private String avatarUrl;

    private String resultImageUrl;

    private String modelProvider;

    private Integer tokensUsed;

    private Boolean customerApproved;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

}