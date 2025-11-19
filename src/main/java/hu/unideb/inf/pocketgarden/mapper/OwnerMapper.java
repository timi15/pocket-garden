package hu.unideb.inf.pocketgarden.mapper;

import hu.unideb.inf.pocketgarden.data.entity.OwnerEntity;
import hu.unideb.inf.pocketgarden.service.dto.request.OwnerReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.OwnerResDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy =
                NullValuePropertyMappingStrategy.IGNORE
)
public interface OwnerMapper {

    OwnerResDto toResponseDto(OwnerEntity ownerEntity);

    OwnerEntity toEntity(OwnerReqDto ownerReqDto);

    List<OwnerResDto> map(List<OwnerEntity> ownerEntities);

}
