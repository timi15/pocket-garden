package hu.unideb.inf.pocketgarden.controller;

import hu.unideb.inf.pocketgarden.service.PlantService;
import hu.unideb.inf.pocketgarden.service.dto.request.PlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.request.UpdatePlantReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantResDto;
import hu.unideb.inf.pocketgarden.service.dto.response.PlantWaterResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Plants", description = "Növények kezeléshez kapcsolódó végpontok")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;

    @Operation(
            summary = "Új növény létrehozása",
            description = "Létrehoz egy új növényt a megadott adatok alapján. "
                    + "Növénytulajdonos nélkül a művelet nem hajtható végre.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Sikeresen létrehozva",
                            content = @Content(schema = @Schema(implementation = PlantResDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Hiányzó vagy érvénytelen növénytulajdonos azonosító. "
                                    + "A növény csak létező növénytulajdonoshoz hozható létre."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "A megadott növénytulajdonos nem található."
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "A megadott nickname már létezik. "
                                    + "A növény nickname értékének egyedinek kell lennie."
                    )
            }
    )
    @PostMapping("/save")
    public ResponseEntity<PlantResDto> save(
            @RequestBody PlantReqDto plantReqDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(plantService.save(plantReqDto));
    }

    @Operation(
            summary = "Összes növény lekérdezése",
            description = "Visszaadja az adatbázisban található összes növényt.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sikeres lekérdezés",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = PlantResDto.class))
                            )
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<PlantResDto>> findAll() {
        return ResponseEntity.ok(plantService.findAll());
    }

    @Operation(
            summary = "Növény lekérdezése ID alapján",
            description = "Visszaadja a megadott azonosítóval rendelkező növényt.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sikeres lekérdezés",
                            content = @Content(schema = @Schema(implementation = PlantResDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Nem található")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PlantResDto> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(plantService.findById(id));
    }

    @Operation(
            summary = "Növények lekérdezése owner ID alapján",
            description = "Visszaadja az adott tulajdonoshoz tartozó növényeket.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sikeres lekérdezés",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = PlantResDto.class))
                            )
                    )
            }
    )
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PlantResDto>> findByOwnerId(
            @PathVariable UUID ownerId
    ) {
        return ResponseEntity.ok(plantService.findByOwnerId(ownerId));
    }

    @Operation(
            summary = "Következő öntözési dátum lekérdezése",
            description = "Visszaadja, hogy mikor kell legközelebb öntözni a növényt.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sikeres lekérdezés",
                            content = @Content(schema = @Schema(implementation = PlantWaterResDto.class))
                    )
            }
    )
    @GetMapping("/watering/next/{id}")
    public ResponseEntity<PlantWaterResDto> nextWateringDate(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(plantService.nextWateringDate(id));
    }

    @Operation(
            summary = "Növény megöntözése",
            description = "Frissíti a növényt úgy, hogy megöntözted.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sikeres művelet",
                            content = @Content(schema = @Schema(implementation = PlantWaterResDto.class))
                    )
            }
    )
    @GetMapping("/watering/{id}")
    public ResponseEntity<PlantWaterResDto> watering(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(plantService.watering(id));
    }

    @Operation(
            summary = "Növény adatainak frissítése",
            description = "Frissíti a megadott növény adatait.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sikeresen frissítve",
                            content = @Content(schema = @Schema(implementation = PlantResDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Nem található")
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<PlantResDto> update(
            @PathVariable UUID id,
            @RequestBody UpdatePlantReqDto updatePlantReqDto
    ) {
        return ResponseEntity.ok(plantService.update(id, updatePlantReqDto));
    }

    @Operation(
            summary = "Növény törlése ID alapján",
            description = "Törli az adott azonosítóval rendelkező növényt.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sikeresen törölve"),
                    @ApiResponse(responseCode = "404", description = "Nem található")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<PlantResDto> deleteById(
            @PathVariable UUID id
    ) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(plantService.deleteById(id));
    }
}
