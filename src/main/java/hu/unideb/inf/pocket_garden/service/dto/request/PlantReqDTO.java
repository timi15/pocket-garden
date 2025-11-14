package hu.unideb.inf.pocket_garden.service.dto.request;

import hu.unideb.inf.pocket_garden.enums.SunlightRequirement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlantReqDTO {

    public static final int MIN_WATERING_FREQUENCY = 1;
    public static final int MAX_WATERING_FREQUENCY = 30;

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    private String nickname;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @Min(MIN_WATERING_FREQUENCY)
    @Max(MAX_WATERING_FREQUENCY)
    @NotNull
    private Integer wateringFrequency;

    @NotNull
    private SunlightRequirement sunlightRequirement;

    @NotNull
    @PastOrPresent
    private LocalDate lastWateredAt;

    @NotNull
    private UUID ownerId;
}
