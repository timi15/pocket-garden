package hu.unideb.inf.pocket_garden.service.impl;

import hu.unideb.inf.pocket_garden.data.entity.OwnerEntity;
import hu.unideb.inf.pocket_garden.data.entity.PlantEntity;
import hu.unideb.inf.pocket_garden.data.repository.OwnerRepository;
import hu.unideb.inf.pocket_garden.data.repository.PlantRepository;
import hu.unideb.inf.pocket_garden.exception.AlreadyExistsException;
import hu.unideb.inf.pocket_garden.exception.NotFoundException;
import hu.unideb.inf.pocket_garden.mapper.PlantMapper;
import hu.unideb.inf.pocket_garden.service.dto.request.PlantReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.request.UpdatePlantReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.PlantResDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.PlantWaterResDTO;
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
    private PlantResDTO plantResDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        id = UUID.randomUUID();
        plantEntity = new PlantEntity();
        plantEntity.setId(id);
        plantEntity.setNickname("Cactus");

        plantResDTO = new PlantResDTO();
        plantResDTO.setId(id);
        plantResDTO.setNickname("Cactus");
    }

    @Test
    void testSaveShouldThrowAlreadyExistsException() {
        PlantReqDTO request = new PlantReqDTO();
        request.setNickname("Cactus");

        when(plantRepository.existsByNickname("Cactus")).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> underTest.save(request));
        verify(plantRepository, never()).save(any());
    }

    @Test
    void testSaveShouldSetDateAndReturnResponse() {
        PlantReqDTO request = new PlantReqDTO();
        request.setNickname("Cactus");
        request.setOwnerId(id);

        OwnerEntity owner = new OwnerEntity();
        owner.setId(id);

        when(plantRepository.existsByNickname("Cactus")).thenReturn(false);
        when(ownerRepository.existsById(id)).thenReturn(true);
        when(ownerRepository.findById(id)).thenReturn(Optional.of(owner));
        when(plantMapper.toEntity(request)).thenReturn(plantEntity);
        when(plantRepository.save(any())).thenReturn(plantEntity);
        when(plantMapper.toResponseDTO(plantEntity)).thenReturn(plantResDTO);

        PlantResDTO result = underTest.save(request);

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
        when(plantMapper.toResponseDTO(plantEntity)).thenReturn(plantResDTO);

        PlantResDTO result = underTest.findById(id);

        assertEquals("Cactus", result.getNickname());
    }

    @Test
    void testFindAllShouldReturnMappedList() {
        when(plantRepository.findAll()).thenReturn(List.of(plantEntity));
        when(plantMapper.map(any())).thenReturn(List.of(plantResDTO));

        List<PlantResDTO> result = underTest.findAll();

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

        List<PlantResDTO> result = underTest.findByOwnerId(id);

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
        when(plantMapper.toPlantWateringResponseDTO(plantEntity)).thenReturn(new PlantWaterResDTO());

        PlantWaterResDTO result = underTest.nextWateringDate(id);

        assertNotNull(result);
    }

    @Test
    void testWateringShouldUpdateDate() {
        when(plantRepository.existsById(id)).thenReturn(true);
        when(plantRepository.findById(id)).thenReturn(Optional.of(plantEntity));
        when(plantMapper.toPlantWateringResponseDTO(plantEntity)).thenReturn(new PlantWaterResDTO());

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
        when(plantMapper.toResponseDTO(plantEntity)).thenReturn(plantResDTO);

        PlantResDTO result = underTest.deleteById(id);

        verify(plantRepository).deleteById(id);
        assertEquals("Cactus", result.getNickname());
    }

    @Test
    void testUpdateShouldThrowWhenNotFound() {
        when(plantRepository.existsById(id)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> underTest.update(id, new UpdatePlantReqDTO()));
    }

    @Test
    void testUpdateShouldReturnUpdatedPlant() {
        UpdatePlantReqDTO updateDTO = new UpdatePlantReqDTO();

        when(plantRepository.existsById(id)).thenReturn(true);
        when(plantRepository.findById(id)).thenReturn(Optional.of(plantEntity));
        when(plantRepository.save(plantEntity)).thenReturn(plantEntity);
        when(plantMapper.toResponseDTO(plantEntity)).thenReturn(plantResDTO);

        PlantResDTO result = underTest.update(id, updateDTO);

        assertEquals("Cactus", result.getNickname());
        verify(plantMapper).updatePlantEntityFromDTO(updateDTO, plantEntity);
    }
}