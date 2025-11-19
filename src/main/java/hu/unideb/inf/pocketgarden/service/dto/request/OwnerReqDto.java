package hu.unideb.inf.pocketgarden.service.dto.request;

import hu.unideb.inf.pocketgarden.enums.ExperienceLevel;
import hu.unideb.inf.pocketgarden.enums.Sex;
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
public class OwnerReqDto {

    @NotBlank
    private Sex sex;

    @NotBlank
    private String username;

    @NotBlank
    private ExperienceLevel experienceLevel;
}
