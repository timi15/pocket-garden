package hu.unideb.inf.pocket_garden.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PlantWaterResDTO {
    private UUID id;
    private String name;
    private String type;
    private String nickname;
    private Integer wateringFrequency;
    private LocalDate lastWateredAt;
    private LocalDate nextWateringDate;
    private UUID ownerId;
}
