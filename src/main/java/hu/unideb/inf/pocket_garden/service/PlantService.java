package hu.unideb.inf.pocket_garden.service;

import hu.unideb.inf.pocket_garden.service.dto.request.PlantReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.request.UpdatePlantReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.PlantResDTO;

import java.util.List;
import java.util.UUID;

public interface PlantService {

    PlantResDTO save(PlantReqDTO plantReqDTO);

    List<PlantResDTO> findAll();

    PlantResDTO findById(UUID id);

    List<PlantResDTO> findByOwnerId(UUID ownerId);

    PlantResDTO deleteById(UUID id);

    PlantResDTO update(UUID id, UpdatePlantReqDTO updatePlantReqDTO);

}
