package goldstar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TryOnJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "garment_id", nullable = false)
    private Garment garment;

    private String sessionCode;

    private String avatarUrl;

    private String resultImageUrl;

    private String modelProvider;

    @Enumerated(EnumType.STRING)
    private TryOnStatus status;

    private Integer tokensUsed;

    private Boolean customerApproved;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;
}