package hu.unideb.inf.pocket_garden.controller;

import hu.unideb.inf.pocket_garden.service.PlantService;
import hu.unideb.inf.pocket_garden.service.dto.request.PlantReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.request.UpdatePlantReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.PlantResDTO;
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
    public ResponseEntity<PlantResDTO> save(
            @RequestBody PlantReqDTO plantReqDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(plantService.save(plantReqDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlantResDTO>> findAll() {
        return ResponseEntity.ok(plantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantResDTO> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(plantService.findById(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PlantResDTO>> findByOwnerId(
            @PathVariable UUID ownerId
    ) {
        return ResponseEntity.ok(plantService.findByOwnerId(ownerId));
    }

    @PutMapping("{id}")
    public ResponseEntity<PlantResDTO> update(
            @PathVariable UUID id,
            @RequestBody UpdatePlantReqDTO updatePlantReqDTO
    ) {
        return ResponseEntity.ok(plantService.update(id, updatePlantReqDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<PlantResDTO> deleteById(
            @PathVariable UUID id
    ) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(plantService.deleteById(id));
    }
}
