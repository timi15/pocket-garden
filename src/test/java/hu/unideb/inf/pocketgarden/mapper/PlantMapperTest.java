package hu.unideb.inf.pocketgarden.mapper;

import hu.unideb.inf.pocketgarden.data.entity.PlantEntity;
import hu.unideb.inf.pocketgarden.service.dto.request.PlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.request.UpdatePlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantResDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantWaterResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlantMapperTest {

    private PlantMapper plantMapper;

    @BeforeEach
    void setUp() {
        plantMapper = Mappers.getMapper(PlantMapper.class);
    }

    @Test
    void testToEntity() {
        PlantReqDto dto = new PlantReqDto();
        dto.setName("Teszt");
        dto.setNickname("teszt");
        dto.setWateringFrequency(3);
        dto.setLastWateredAt(LocalDate.of(2025, 11, 10));

        PlantEntity entity = plantMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Teszt", entity.getName());
        assertEquals("teszt", entity.getNickname());
        assertEquals(3, entity.getWateringFrequency());
        assertEquals(LocalDate.of(2025, 11, 10), entity.getLastWateredAt());
    }

    @Test
    void testToResponseDto() {
        PlantEntity entity = new PlantEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("Teszt");
        entity.setNickname("teszt");
        entity.setWateringFrequency(2);
        entity.setLastWateredAt(LocalDate.of(2025, 11, 12));

        PlantResDto dto = plantMapper.toResponseDto(entity);

        assertNotNull(dto);
        assertEquals("Teszt", dto.getName());
        assertEquals("teszt", dto.getNickname());
        assertEquals(2, dto.getWateringFrequency());
    }

    @Test
    void testToPlantWateringResponseDto_WithAfterMapping() {
        PlantEntity entity = new PlantEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("Teszt");
        entity.setLastWateredAt(LocalDate.of(2025, 11, 10));
        entity.setWateringFrequency(5);

        PlantWaterResDto dto = plantMapper.toPlantWateringResponseDto(entity);

        assertNotNull(dto);
        assertEquals(LocalDate.of(2025, 11, 15), dto.getNextWateringDate());
    }

    @Test
    void testUpdatePlantEntityFromDto() {
        PlantEntity entity = new PlantEntity();
        entity.setName("Old");
        entity.setNickname("Old nick");
        entity.setWateringFrequency(2);

        UpdatePlantReqDto updateDTO = new UpdatePlantReqDto();
        updateDTO.setWateringFrequency(4);

        plantMapper.updatePlantEntityFromDto(updateDTO, entity);

        assertEquals(4, entity.getWateringFrequency());
        assertEquals("Old nick", entity.getNickname());
    }

    @Test
    void testMapList() {
        PlantEntity entity1 = new PlantEntity();
        entity1.setName("Teszt1");
        entity1.setNickname("teszt1");

        PlantEntity entity2 = new PlantEntity();
        entity2.setName("Teszt2");
        entity2.setNickname("teszt2");

        List<PlantResDto> dtos = plantMapper.map(List.of(entity1, entity2));

        assertEquals(2, dtos.size());
        assertEquals("Teszt1", dtos.get(0).getName());
        assertEquals("Teszt2", dtos.get(1).getName());
    }
}