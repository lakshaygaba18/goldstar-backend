package goldstar_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvatarResponse {

    private Long id;

    private String avatarId;

    private String status;

    private String previewImageUrl;

}