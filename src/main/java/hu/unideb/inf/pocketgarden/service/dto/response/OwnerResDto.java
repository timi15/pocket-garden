package hu.unideb.inf.pocketgarden.service.dto.response;

import hu.unideb.inf.pocketgarden.enums.ExperienceLevel;
import hu.unideb.inf.pocketgarden.enums.Sex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OwnerResDto {
    private UUID id;
    private Sex sex;
    private String username;
    private ExperienceLevel experienceLevel;
}
