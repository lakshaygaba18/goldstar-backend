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
public class CustomerPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;

    private String photoType;
    @Column(name = "image", columnDefinition = "BYTEA")
    private byte[] image;
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}