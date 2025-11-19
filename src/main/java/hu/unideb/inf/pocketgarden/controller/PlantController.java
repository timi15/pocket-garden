package hu.unideb.inf.pocketgarden.controller;

import hu.unideb.inf.pocketgarden.service.PlantService;
import hu.unideb.inf.pocketgarden.service.dto.request.PlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.request.UpdatePlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantResDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantWaterResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/pocket-garden/plants")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;

    @PostMapping("/save")
    public ResponseEntity<PlantResDto> save(
            @RequestBody PlantReqDto plantReqDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(plantService.save(plantReqDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlantResDto>> findAll() {
        return ResponseEntity.ok(plantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantResDto> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(plantService.findById(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PlantResDto>> findByOwnerId(
            @PathVariable UUID ownerId
    ) {
        return ResponseEntity.ok(plantService.findByOwnerId(ownerId));
    }

    @GetMapping("/watering/next/{id}")
    public ResponseEntity<PlantWaterResDto> nextWateringDate(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(plantService.nextWateringDate(id));
    }

    @GetMapping("/watering/{id}")
    public ResponseEntity<PlantWaterResDto> watering(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(plantService.watering(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<PlantResDto> update(
            @PathVariable UUID id,
            @RequestBody UpdatePlantReqDto updatePlantReqDto
    ) {
        return ResponseEntity.ok(plantService.update(id, updatePlantReqDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<PlantResDto> deleteById(
            @PathVariable UUID id
    ) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(plantService.deleteById(id));
    }
}
