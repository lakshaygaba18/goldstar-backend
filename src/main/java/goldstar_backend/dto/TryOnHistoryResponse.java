package goldstar_backend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TryOnHistoryResponse {

    private Long id;

    private String garmentName;

    private String garmentCode;

    private String status;

    private String generatedImage;

    private LocalDateTime createdAt;
}