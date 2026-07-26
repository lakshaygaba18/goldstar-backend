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
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private Integer totalTokens;

    private Integer tokensRemaining;

    private String planName;

    private Double amountPaid;

    private LocalDateTime purchasedAt;

    private LocalDateTime lastRenewedAt;

    private boolean active;
}