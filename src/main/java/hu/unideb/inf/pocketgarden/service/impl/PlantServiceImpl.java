package hu.unideb.inf.pocketgarden.service.impl;

import hu.unideb.inf.pocketgarden.data.entity.PlantEntity;
import hu.unideb.inf.pocketgarden.data.repository.OwnerRepository;
import hu.unideb.inf.pocketgarden.data.repository.PlantRepository;
import hu.unideb.inf.pocketgarden.exception.AlreadyExistsException;
import hu.unideb.inf.pocketgarden.exception.MissingOwnerException;
import hu.unideb.inf.pocketgarden.exception.NotFoundException;
import hu.unideb.inf.pocketgarden.mapper.PlantMapper;
import hu.unideb.inf.pocketgarden.service.PlantService;
import hu.unideb.inf.pocketgarden.service.dto.request.PlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.request.UpdatePlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantResDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantWaterResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;
    private final OwnerRepository ownerRepository;
    private final PlantMapper plantMapper;

    @Override
    public PlantResDto save(PlantReqDto plantReqDto) {

        if (plantRepository.existsByNickname(plantReqDto.getNickname())) {
            throw new AlreadyExistsException(
                    "Plant already exists with this nickname: "
                            + plantReqDto.getNickname()
            );
        }

        if (null == plantReqDto.getOwnerId()) {
            throw new MissingOwnerException(
                    "Owner ID is required to save a plant."
            );
        }

        if (!ownerRepository.existsById(plantReqDto.getOwnerId())) {
            throw new NotFoundException(
                    "Owner not found with id: "
                            + plantReqDto.getOwnerId()
            );
        }

        if (null == plantReqDto.getLastWateredAt()) {
            plantReqDto.setLastWateredAt(LocalDate.now());
        }

        PlantEntity plantEntity = plantMapper.toEntity(plantReqDto);
        plantEntity.setOwner(
                ownerRepository
                        .findById(plantReqDto.getOwnerId()).get()
        );

        return plantMapper.toResponseDto(plantRepository.save(plantEntity));
    }

    @Override
    public List<PlantResDto> findAll() {
        List<PlantEntity> plantEntities = plantRepository.findAll();
        return plantMapper.map(plantEntities);
    }

    @Override
    public PlantResDto findById(UUID id) {
        if (!plantRepository.existsById(id)) {
            throw new NotFoundException("Plant not found with id: " + id);
        }
        return plantMapper.toResponseDto(plantRepository.findById(id).get());
    }

    @Override
    public List<PlantResDto> findByOwnerId(UUID ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
            throw new NotFoundException("Owner not found with id: " + ownerId);
        }
        return plantMapper.map(plantRepository.findByOwnerId(ownerId));
    }

    @Override
    public PlantWaterResDto nextWateringDate(UUID id) {
        if (!plantRepository.existsById(id)) {
            throw new NotFoundException("Plant not found with id: " + id);
        }

        return plantMapper
                .toPlantWateringResponseDto(
                        plantRepository.findById(id).get()
                );
    }

    @Override
    public PlantWaterResDto watering(UUID id) {
        if (!plantRepository.existsById(id)) {
            throw new NotFoundException("Plant not found with id: " + id);
        }

        PlantEntity plantEntity = plantRepository.findById(id).get();
        plantEntity.setLastWateredAt(LocalDate.now());

        plantRepository.save(plantEntity);

        return plantMapper.toPlantWateringResponseDto(plantEntity);
    }

    @Override
    public PlantResDto deleteById(UUID id) {
        if (!plantRepository.existsById(id)) {
            throw new NotFoundException("Plant not found with id: " + id);
        }

        PlantEntity plantEntity = plantRepository.findById(id).get();
        plantRepository.deleteById(id);

        return plantMapper.toResponseDto(plantEntity);
    }

    @Override
    public PlantResDto update(UUID id, UpdatePlantReqDto updatePlantReqDto) {
        if (!plantRepository.existsById(id)) {
            throw new NotFoundException("Plant not found with id: " + id);
        }

        PlantEntity existing = plantRepository.findById(id).get();
        plantMapper.updatePlantEntityFromDto(updatePlantReqDto, existing);
        PlantEntity updated = plantRepository.save(existing);

        return plantMapper.toResponseDto(updated);
    }

}
