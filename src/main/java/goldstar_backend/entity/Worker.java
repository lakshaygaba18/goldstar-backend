package goldstar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Worker {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String workerCode;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    private String pin;


}