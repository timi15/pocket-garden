package hu.unideb.inf.pocket_garden.service.dto.request;

import hu.unideb.inf.pocket_garden.enums.SunlightRequirement;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdatePlantReqDTO {

    public static final int MIN_WATERING_FREQUENCY = 1;
    public static final int MAX_WATERING_FREQUENCY = 30;

    private String imageUrl;

    @Min(MIN_WATERING_FREQUENCY)
    @Max(MAX_WATERING_FREQUENCY)
    private Integer wateringFrequency;

    private SunlightRequirement sunlightRequirement;

    @PastOrPresent
    private LocalDate lastWateredAt;
}
