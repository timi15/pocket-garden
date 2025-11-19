package hu.unideb.inf.pocketgarden.service.dto.response;

import hu.unideb.inf.pocketgarden.enums.SunlightRequirement;
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
public class PlantResDto {
    private UUID id;
    private String name;
    private String type;
    private String nickname;
    private String description;
    private String imageUrl;
    private Integer wateringFrequency;
    private SunlightRequirement sunlightRequirement;
    private LocalDate lastWateredAt;
    private UUID ownerId;
}
