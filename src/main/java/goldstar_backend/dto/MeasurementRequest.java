package goldstar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementRequest {

    private Double height;
    private Double weight;

    private Double neck;
    private Double shoulder;

    private Double chest;
    private Double waist;
    private Double hip;

    private Double sleeveLength;
    private Double armLength;

    private Double wrist;

    private Double thigh;
    private Double calf;

    private Double inseam;
    private Double outseam;

    private Double ankle;

    private String bodyType;
    private String gender;


}