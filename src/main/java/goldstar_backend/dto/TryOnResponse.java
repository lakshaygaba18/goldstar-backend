package goldstar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TryOnResponse {

    private Long id;

    private String generatedImage;

    private String video360;
}