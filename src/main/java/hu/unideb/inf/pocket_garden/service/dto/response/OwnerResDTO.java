package hu.unideb.inf.pocket_garden.service.dto.response;

import hu.unideb.inf.pocket_garden.enums.ExperienceLevel;
import hu.unideb.inf.pocket_garden.enums.Sex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OwnerResDTO {
    private UUID id;
    private Sex sex;
    private String username;
    private ExperienceLevel experienceLevel;
}
