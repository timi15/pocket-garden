package hu.unideb.inf.pocketgarden.controller;

import hu.unideb.inf.pocketgarden.service.OwnerService;
import hu.unideb.inf.pocketgarden.service.dto.request.OwnerReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.OwnerResDto;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/pocket-garden/owners")
@Tag(name = "Owner API", description = "Növénytulajdonosok kezeléshez kapcsolódó végpontok")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @Operation(
            summary = "Új növénytulajdonos létrehozása",
            description = "Létrehoz egy új növénytulajdonost a megadott adatok alapján.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Sikeresen létrehozva",
                            content = @Content(schema = @Schema(implementation = OwnerResDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Már létezik ilyen nevű növénytulajdonos"
                    )
            }
    )
    @PostMapping("/save")
    public ResponseEntity<OwnerResDto> save(
            @RequestBody OwnerReqDto ownerReqDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ownerService.save(ownerReqDto));
    }

    @Operation(
            summary = "Összes növénytulajdonos lekérdezése",
            description = "Visszaadja az adatbázisban lévő összes növénytulajdonost.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sikeres lekérdezés",
                            content = @Content(array =
                            @ArraySchema(schema = @Schema(implementation = OwnerResDto.class)))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<OwnerResDto>> findAll() {
        return ResponseEntity.ok(ownerService.findAll());
    }


    @Operation(
            summary = "Növénytulajdonos lekérdezése ID alapján",
            description = "Visszaadja az adott azonosítóval rendelkező növénytulajdonost.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sikeres lekérdezés",
                            content = @Content(schema = @Schema(implementation = OwnerResDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Nem található"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OwnerResDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ownerService.findById(id));
    }

}
