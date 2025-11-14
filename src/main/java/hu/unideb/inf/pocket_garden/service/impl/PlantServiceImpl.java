package hu.unideb.inf.pocket_garden.service.impl;

import hu.unideb.inf.pocket_garden.data.entity.PlantEntity;
import hu.unideb.inf.pocket_garden.data.repository.OwnerRepository;
import hu.unideb.inf.pocket_garden.data.repository.PlantRepository;
import hu.unideb.inf.pocket_garden.exception.AlreadyExistsException;
import hu.unideb.inf.pocket_garden.exception.MissingOwnerException;
import hu.unideb.inf.pocket_garden.exception.NotFoundException;
import hu.unideb.inf.pocket_garden.mapper.PlantMapper;
import hu.unideb.inf.pocket_garden.service.PlantService;
import hu.unideb.inf.pocket_garden.service.dto.request.PlantReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.request.UpdatePlantReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.PlantResDTO;
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
    public PlantResDTO save(PlantReqDTO plantReqDTO) {

        if (plantRepository.existsByNickname(plantReqDTO.getNickname())) {
            throw new AlreadyExistsException(
                    "Plant already exists with this nickname: "
                            + plantReqDTO.getNickname()
            );
        }

        if (null == plantReqDTO.getOwnerId()) {
            throw new MissingOwnerException(
                    "Owner ID is required to save a plant."
            );
        }

        if (!ownerRepository.existsById(plantReqDTO.getOwnerId())) {
            throw new NotFoundException(
                    "Owner not found with id: "
                            + plantReqDTO.getOwnerId()
            );
        }

        if (null == plantReqDTO.getLastWateredAt()) {
            plantReqDTO.setLastWateredAt(LocalDate.now());
        }

        PlantEntity plantEntity = plantMapper.toEntity(plantReqDTO);
        plantEntity.setOwner(
                ownerRepository
                        .findById(plantReqDTO.getOwnerId()).get()
        );

        return plantMapper.toResponseDTO(plantRepository.save(plantEntity));
    }

    @Override
    public List<PlantResDTO> findAll() {
        List<PlantEntity> plantEntities = plantRepository.findAll();
        return plantMapper.map(plantEntities);
    }

    @Override
    public PlantResDTO findById(UUID id) {
        if (!plantRepository.existsById(id)) {
            throw new NotFoundException("Plant not found with id: " + id);
        }
        return plantMapper.toResponseDTO(plantRepository.findById(id).get());
    }

    @Override
    public List<PlantResDTO> findByOwnerId(UUID ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
            throw new NotFoundException("Owner not found with id: " + ownerId);
        }
        return plantMapper.map(plantRepository.findByOwnerId(ownerId));
    }

    @Override
    public PlantResDTO deleteById(UUID id) {
        if (!plantRepository.existsById(id)) {
            throw new NotFoundException("Plant not found with id: " + id);
        }

        PlantEntity plantEntity = plantRepository.findById(id).get();
        plantRepository.deleteById(id);

        return plantMapper.toResponseDTO(plantEntity);
    }

    @Override
    public PlantResDTO update(UUID id, UpdatePlantReqDTO updatePlantReqDTO) {
        if (!plantRepository.existsById(id)) {
            throw new NotFoundException("Plant not found with id: " + id);
        }

        PlantEntity existing = plantRepository.findById(id).get();
        plantMapper.updatePlantEntityFromDTO(updatePlantReqDTO, existing);
        PlantEntity updated = plantRepository.save(existing);

        return plantMapper.toResponseDTO(updated);
    }

}
