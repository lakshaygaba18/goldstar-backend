package goldstar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LookbookResponse {

    private Long id;

    private String title;

    private String pdfPath;

}