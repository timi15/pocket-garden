package hu.unideb.inf.pocketgarden.service;

import hu.unideb.inf.pocketgarden.service.dto.request.PlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.request.UpdatePlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantResDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantWaterResDto;

import java.util.List;
import java.util.UUID;

public interface PlantService {

    PlantResDto save(PlantReqDto plantReqDto);

    List<PlantResDto> findAll();

    PlantResDto findById(UUID id);

    List<PlantResDto> findByOwnerId(UUID ownerId);

    PlantWaterResDto nextWateringDate(UUID id);

    PlantWaterResDto watering(UUID id);

    PlantResDto deleteById(UUID id);

    PlantResDto update(UUID id, UpdatePlantReqDto updatePlantReqDto);

}
