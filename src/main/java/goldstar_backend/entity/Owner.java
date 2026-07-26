package goldstar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shopName;

    private String ownerName;

    @Column(unique = true)
    private String email;

    private String password;

    private String phoneNumber;


    private String address;

    private String phone;



    private String logo;

}