package goldstar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String avatarId;

    private String status;

    private String previewImageUrl;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}