package hu.unideb.inf.pocketgarden.mapper;

import hu.unideb.inf.pocketgarden.data.entity.OwnerEntity;
import hu.unideb.inf.pocketgarden.enums.ExperienceLevel;
import hu.unideb.inf.pocketgarden.enums.Sex;
import hu.unideb.inf.pocketgarden.service.dto.request.OwnerReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.OwnerResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapperTest {

    private OwnerMapper ownerMapper;

    @BeforeEach
    void setUp() {
        ownerMapper = Mappers.getMapper(OwnerMapper.class);
    }

    @Test
    void testToEntity() {
        OwnerReqDto request = new OwnerReqDto();
        request.setUsername("Teszt");
        request.setSex(Sex.FEMALE);
        request.setExperienceLevel(ExperienceLevel.BEGINNER);

        OwnerEntity entity = ownerMapper.toEntity(request);

        assertNotNull(entity);
        assertEquals("Teszt", entity.getUsername());
        assertEquals("FEMALE", entity.getSex().toString());
        assertEquals("BEGINNER", entity.getExperienceLevel().toString());
    }

    @Test
    void testToResponseDto() {
        OwnerEntity entity = new OwnerEntity();
        entity.setUsername("Teszt");
        entity.setSex(Sex.FEMALE);
        entity.setExperienceLevel(ExperienceLevel.BEGINNER);

        OwnerResDto dto = ownerMapper.toResponseDto(entity);

        assertNotNull(dto);
        assertEquals("Teszt", dto.getUsername());
        assertEquals("FEMALE", dto.getSex().toString());
        assertEquals("BEGINNER", dto.getExperienceLevel().toString());
        assertEquals(entity.getId(), dto.getId());
    }

    @Test
    void testMapList() {
        OwnerEntity entity1 = new OwnerEntity();
        entity1.setId(UUID.randomUUID());
        entity1.setUsername("Teszt1");
        entity1.setSex(Sex.FEMALE);
        entity1.setExperienceLevel(ExperienceLevel.BEGINNER);

        OwnerEntity entity2 = new OwnerEntity();
        entity2.setId(UUID.randomUUID());
        entity2.setUsername("Teszt2");
        entity2.setSex(Sex.MALE);
        entity2.setExperienceLevel(ExperienceLevel.MASTER);

        List<OwnerResDto> dtos = ownerMapper.map(List.of(entity1, entity2));

        assertEquals(2, dtos.size());
        assertEquals("Teszt1", dtos.get(0).getUsername());
        assertEquals("Teszt2", dtos.get(1).getUsername());
    }

}