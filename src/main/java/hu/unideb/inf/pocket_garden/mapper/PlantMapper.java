package hu.unideb.inf.pocket_garden.mapper;

import hu.unideb.inf.pocket_garden.data.entity.PlantEntity;
import hu.unideb.inf.pocket_garden.service.dto.request.PlantReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.request.UpdatePlantReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.PlantResDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.PlantWaterResDTO;
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
    PlantResDTO toResponseDTO(PlantEntity plantEntity);

    PlantEntity toEntity(PlantReqDTO plantReqDTO);

    List<PlantResDTO> map(List<PlantEntity> plantEntities);

    void updatePlantEntityFromDTO(
            UpdatePlantReqDTO dto,
            @MappingTarget PlantEntity plantEntity
    );

    @Mapping(
            source = "owner.id",
            target = "ownerId"
    )
    PlantWaterResDTO toPlantWateringResponseDTO(PlantEntity plantEntity);

    @AfterMapping
    default void calculateNextWateringDate(
            PlantEntity plant,
            @MappingTarget PlantWaterResDTO dto
    ) {
        dto.setNextWateringDate(
                plant.getLastWateredAt().plusDays(plant.getWateringFrequency())
        );
    }

}
