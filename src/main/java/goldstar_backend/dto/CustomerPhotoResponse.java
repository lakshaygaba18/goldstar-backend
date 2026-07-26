package goldstar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPhotoResponse {

    private Long id;

    private String imageName;

    private String photoType;
}