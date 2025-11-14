package hu.unideb.inf.pocket_garden.mapper;

import hu.unideb.inf.pocket_garden.data.entity.OwnerEntity;
import hu.unideb.inf.pocket_garden.service.dto.request.OwnerReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.OwnerResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy =
                NullValuePropertyMappingStrategy.IGNORE
)
public interface OwnerMapper {

    OwnerResDTO toResponseDTO(OwnerEntity ownerEntity);

    OwnerEntity toEntity(OwnerReqDTO ownerReqDTO);

    List<OwnerResDTO> map(List<OwnerEntity> ownerEntities);

}
