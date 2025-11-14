package hu.unideb.inf.pocket_garden.service.impl;

import hu.unideb.inf.pocket_garden.data.entity.OwnerEntity;
import hu.unideb.inf.pocket_garden.data.repository.OwnerRepository;
import hu.unideb.inf.pocket_garden.enums.ExperienceLevel;
import hu.unideb.inf.pocket_garden.enums.Sex;
import hu.unideb.inf.pocket_garden.exception.AlreadyExistsException;
import hu.unideb.inf.pocket_garden.exception.NotFoundException;
import hu.unideb.inf.pocket_garden.mapper.OwnerMapper;
import hu.unideb.inf.pocket_garden.service.dto.request.OwnerReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.OwnerResDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private OwnerMapper ownerMapper;

    @InjectMocks
    private OwnerServiceImpl underTest;

    @Test
    void testSaveShouldSaveSuccessfully() {
        // Given
        OwnerReqDTO requestDTO = new OwnerReqDTO(Sex.FEMALE, "teszt", ExperienceLevel.BEGINNER);
        OwnerEntity entity = new OwnerEntity(UUID.randomUUID(), Sex.FEMALE, "teszt", ExperienceLevel.BEGINNER, List.of());
        OwnerResDTO responseDTO = new OwnerResDTO(entity.getId(), Sex.FEMALE, "teszt", ExperienceLevel.BEGINNER);

        when(ownerRepository.existsByUsername(requestDTO.getUsername())).thenReturn(false);
        when(ownerMapper.toEntity(requestDTO)).thenReturn(entity);
        when(ownerRepository.save(entity)).thenReturn(entity);
        when(ownerMapper.toResponseDTO(entity)).thenReturn(responseDTO);

        // When
        OwnerResDTO actual = underTest.save(requestDTO);

        // Then
        assertNotNull(actual);
        assertEquals("teszt", actual.getUsername());
        verify(ownerRepository).save(entity);
    }

    @Test
    void testSaveShouldThrowAlreadyExistsException() {
        // Given
        OwnerReqDTO requestDTO = new OwnerReqDTO(Sex.FEMALE, "teszt", ExperienceLevel.BEGINNER);
        when(ownerRepository.existsByUsername("teszt")).thenReturn(true);

        // When & Then
        assertThrows(AlreadyExistsException.class, () -> underTest.save(requestDTO));
        verify(ownerRepository, never()).save(any());
    }

    @Test
    void testFindByIdShouldReturnOwner() {
        // Given
        UUID id = UUID.randomUUID();
        OwnerEntity entity = new OwnerEntity(UUID.randomUUID(), Sex.FEMALE, "teszt", ExperienceLevel.BEGINNER, List.of());
        OwnerResDTO responseDTO = new OwnerResDTO(entity.getId(), Sex.FEMALE, "teszt", ExperienceLevel.BEGINNER);

        when(ownerRepository.existsById(id)).thenReturn(true);
        when(ownerRepository.findById(id)).thenReturn(Optional.of(entity));
        when(ownerMapper.toResponseDTO(entity)).thenReturn(responseDTO);

        // When
        OwnerResDTO result = underTest.findById(id);

        // Then
        assertNotNull(result);
        assertEquals("teszt", result.getUsername());
        verify(ownerRepository).findById(id);
    }

    @Test
    void testFindByIdShouldThrowNotFoundException() {
        // Given
        UUID id = UUID.randomUUID();
        when(ownerRepository.existsById(id)).thenReturn(false);

        // When & Then
        assertThrows(NotFoundException.class, () -> underTest.findById(id));
        verify(ownerRepository, never()).findById(id);
    }

    @Test
    void testFindAllShouldReturnList() {
        // Given
        OwnerEntity entity1 = new OwnerEntity(UUID.randomUUID(), Sex.FEMALE, "teszt", ExperienceLevel.BEGINNER, List.of());
        OwnerEntity entity2 = new OwnerEntity(UUID.randomUUID(), Sex.MALE, "teszt2", ExperienceLevel.ADVANCED, List.of());
        List<OwnerEntity> entities = List.of(entity1, entity2);

        OwnerResDTO dto1 = new OwnerResDTO(entity1.getId(), entity1.getSex(), entity1.getUsername(), entity1.getExperienceLevel());
        OwnerResDTO dto2 = new OwnerResDTO(entity2.getId(), entity2.getSex(), entity2.getUsername(), entity2.getExperienceLevel());
        List<OwnerResDTO> expectedDTOs = List.of(dto1, dto2);

        when(ownerRepository.findAll()).thenReturn(entities);
        when(ownerMapper.map(entities)).thenReturn(expectedDTOs);

        // When
        List<OwnerResDTO> actual = underTest.findAll();

        // Then
        assertEquals(2, actual.size());
        verify(ownerRepository).findAll();
    }
}