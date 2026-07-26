package goldstar_backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDashboardResponse {

    private Long customerId;

    private String customerName;

    private String phoneNumber;

    private MeasurementResponse measurement;

    private List<CustomerPhotoResponse> photos;

    private List<OutfitResponse> outfits;

    private List<TryOnResponse> tryOns;

}