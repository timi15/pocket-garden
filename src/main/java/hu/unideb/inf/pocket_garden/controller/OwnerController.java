package hu.unideb.inf.pocket_garden.controller;

import hu.unideb.inf.pocket_garden.service.OwnerService;
import hu.unideb.inf.pocket_garden.service.dto.request.OwnerReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.OwnerResDTO;
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
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/save")
    public ResponseEntity<OwnerResDTO> save(
            @RequestBody OwnerReqDTO ownerReqDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ownerService.save(ownerReqDTO));
    }

    @GetMapping
    public ResponseEntity<List<OwnerResDTO>> findAll() {
        return ResponseEntity.ok(ownerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ownerService.findById(id));
    }

}
