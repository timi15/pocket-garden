package hu.unideb.inf.pocketgarden.service.impl;

import hu.unideb.inf.pocketgarden.data.entity.OwnerEntity;
import hu.unideb.inf.pocketgarden.data.entity.PlantEntity;
import hu.unideb.inf.pocketgarden.data.repository.OwnerRepository;
import hu.unideb.inf.pocketgarden.data.repository.PlantRepository;
import hu.unideb.inf.pocketgarden.exception.AlreadyExistsException;
import hu.unideb.inf.pocketgarden.exception.NotFoundException;
import hu.unideb.inf.pocketgarden.mapper.PlantMapper;
import hu.unideb.inf.pocketgarden.service.dto.request.PlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.request.UpdatePlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantResDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantWaterResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

class PlantServiceImplTest {

    @Mock
    private PlantRepository plantRepository;
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private PlantMapper plantMapper;

    @InjectMocks
    private PlantServiceImpl underTest;

    private UUID id;
    private PlantEntity plantEntity;
    private PlantResDto plantResDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        id = UUID.randomUUID();
        plantEntity = new PlantEntity();
        plantEntity.setId(id);
        plantEntity.setNickname("Cactus");

        plantResDTO = new PlantResDto();
        plantResDTO.setId(id);
        plantResDTO.setNickname("Cactus");
    }

    @Test
    void testSaveShouldThrowAlreadyExistsException() {
        PlantReqDto request = new PlantReqDto();
        request.setNickname("Cactus");

        when(plantRepository.existsByNickname("Cactus")).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> underTest.save(request));
        verify(plantRepository, never()).save(any());
    }

    @Test
    void testSaveShouldSetDateAndReturnResponse() {
        PlantReqDto request = new PlantReqDto();
        request.setNickname("Cactus");
        request.setOwnerId(id);

        OwnerEntity owner = new OwnerEntity();
        owner.setId(id);

        when(plantRepository.existsByNickname("Cactus")).thenReturn(false);
        when(ownerRepository.existsById(id)).thenReturn(true);
        when(ownerRepository.findById(id)).thenReturn(Optional.of(owner));
        when(plantMapper.toEntity(request)).thenReturn(plantEntity);
        when(plantRepository.save(any())).thenReturn(plantEntity);
        when(plantMapper.toResponseDto(plantEntity)).thenReturn(plantResDTO);

        PlantResDto result = underTest.save(request);

        assertNotNull(result);
        assertEquals("Cactus", result.getNickname());
        verify(plantRepository).save(any());
    }

    @Test
    void testFindByIdShouldThrowNotFound() {
        when(plantRepository.existsById(id)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> underTest.findById(id));
    }

    @Test
    void testFindByIdShouldReturnPlant() {
        when(plantRepository.existsById(id)).thenReturn(true);
        when(plantRepository.findById(id)).thenReturn(Optional.of(plantEntity));
        when(plantMapper.toResponseDto(plantEntity)).thenReturn(plantResDTO);

        PlantResDto result = underTest.findById(id);

        assertEquals("Cactus", result.getNickname());
    }

    @Test
    void testFindAllShouldReturnMappedList() {
        when(plantRepository.findAll()).thenReturn(List.of(plantEntity));
        when(plantMapper.map(any())).thenReturn(List.of(plantResDTO));

        List<PlantResDto> result = underTest.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void testFindByOwnerIdShouldThrowWhenOwnerNotExists() {
        when(ownerRepository.existsById(id)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> underTest.findByOwnerId(id));
    }

    @Test
    void testFindByOwnerIdShouldReturnPlants() {
        when(ownerRepository.existsById(id)).thenReturn(true);
        when(plantRepository.findByOwnerId(id)).thenReturn(List.of(plantEntity));
        when(plantMapper.map(any())).thenReturn(List.of(plantResDTO));

        List<PlantResDto> result = underTest.findByOwnerId(id);

        assertEquals(1, result.size());
    }

    @Test
    void testNextWateringDateShouldThrowWhenNotFound() {
        when(plantRepository.existsById(id)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> underTest.nextWateringDate(id));
    }

    @Test
    void testNextWateringDateShouldReturnResponse() {
        when(plantRepository.existsById(id)).thenReturn(true);
        when(plantRepository.findById(id)).thenReturn(Optional.of(plantEntity));
        when(plantMapper.toPlantWateringResponseDto(plantEntity)).thenReturn(new PlantWaterResDto());

        PlantWaterResDto result = underTest.nextWateringDate(id);

        assertNotNull(result);
    }

    @Test
    void testWateringShouldUpdateDate() {
        when(plantRepository.existsById(id)).thenReturn(true);
        when(plantRepository.findById(id)).thenReturn(Optional.of(plantEntity));
        when(plantMapper.toPlantWateringResponseDto(plantEntity)).thenReturn(new PlantWaterResDto());

        underTest.watering(id);

        verify(plantRepository).save(plantEntity);
        assertEquals(LocalDate.now(), plantEntity.getLastWateredAt());
    }

    @Test
    void testDeleteByIdShouldThrowWhenNotFound() {
        when(plantRepository.existsById(id)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> underTest.deleteById(id));
    }

    @Test
    void testDeleteByIdShouldReturnDeletedPlant() {
        when(plantRepository.existsById(id)).thenReturn(true);
        when(plantRepository.findById(id)).thenReturn(Optional.of(plantEntity));
        when(plantMapper.toResponseDto(plantEntity)).thenReturn(plantResDTO);

        PlantResDto result = underTest.deleteById(id);

        verify(plantRepository).deleteById(id);
        assertEquals("Cactus", result.getNickname());
    }

    @Test
    void testUpdateShouldThrowWhenNotFound() {
        when(plantRepository.existsById(id)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> underTest.update(id, new UpdatePlantReqDto()));
    }

    @Test
    void testUpdateShouldReturnUpdatedPlant() {
        UpdatePlantReqDto updateDTO = new UpdatePlantReqDto();

        when(plantRepository.existsById(id)).thenReturn(true);
        when(plantRepository.findById(id)).thenReturn(Optional.of(plantEntity));
        when(plantRepository.save(plantEntity)).thenReturn(plantEntity);
        when(plantMapper.toResponseDto(plantEntity)).thenReturn(plantResDTO);

        PlantResDto result = underTest.update(id, updateDTO);

        assertEquals("Cactus", result.getNickname());
        verify(plantMapper).updatePlantEntityFromDto(updateDTO, plantEntity);
    }
}