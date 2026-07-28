package goldstar_backend.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String shopName;
    private String ownerName;
    private String email;
    private String password;
    private String phoneNumber;
}