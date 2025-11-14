package hu.unideb.inf.pocket_garden.mapper;

import hu.unideb.inf.pocket_garden.data.entity.PlantEntity;
import hu.unideb.inf.pocket_garden.service.dto.request.PlantReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.PlantResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;


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

}
