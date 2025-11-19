package hu.unideb.inf.pocketgarden.mapper;

import hu.unideb.inf.pocketgarden.data.entity.PlantEntity;
import hu.unideb.inf.pocketgarden.service.dto.request.PlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.request.UpdatePlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantResDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantWaterResDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.AfterMapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy =
                NullValuePropertyMappingStrategy.IGNORE)
public interface PlantMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    PlantResDto toResponseDto(PlantEntity plantEntity);

    PlantEntity toEntity(PlantReqDto plantReqDto);

    List<PlantResDto> map(List<PlantEntity> plantEntities);

    void updatePlantEntityFromDto(
            UpdatePlantReqDto dto,
            @MappingTarget PlantEntity plantEntity
    );

    @Mapping(
            source = "owner.id",
            target = "ownerId"
    )
    PlantWaterResDto toPlantWateringResponseDto(PlantEntity plantEntity);

    @AfterMapping
    default void calculateNextWateringDate(
            PlantEntity plant,
            @MappingTarget PlantWaterResDto dto
    ) {
        dto.setNextWateringDate(
                plant.getLastWateredAt().plusDays(plant.getWateringFrequency())
        );
    }

}
