package goldstar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Garment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String garmentCode;

    private String name;

    private String category;

    private String size;

    private Double price;

    private String primaryImage;

    private boolean active;

    private Integer stockQuantity; // NEW — kitni pieces available hain

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

}