package hu.unideb.inf.pocket_garden.service.dto.request;

import hu.unideb.inf.pocket_garden.enums.ExperienceLevel;
import hu.unideb.inf.pocket_garden.enums.Sex;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Setter
public class OwnerReqDTO {

    @NotBlank
    private Sex sex;

    @NotBlank
    private String username;

    @NotBlank
    private ExperienceLevel experienceLevel;
}
