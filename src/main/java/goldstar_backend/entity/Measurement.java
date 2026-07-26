package goldstar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic
    private Double height;
    private Double weight;

    // Upper Body
    private Double neck;
    private Double shoulder;
    private Double chest;
    private Double sleeveLength;
    private Double armLength;
    private Double wrist;
    private Double bicep;

    // Lower Body
    private Double waist;
    private Double hip;
    private Double thigh;
    private Double calf;
    private Double inseam;
    private Double outseam;
    private Double ankle;

    // AI / Avatar
    private String bodyType;
    private String gender;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}