package hu.unideb.inf.pocket_garden.service;

import hu.unideb.inf.pocket_garden.service.dto.request.OwnerReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.OwnerResDTO;

import java.util.List;
import java.util.UUID;

public interface OwnerService {

    OwnerResDTO save(OwnerReqDTO ownerReqDTO);

    OwnerResDTO findById(UUID id);

    List<OwnerResDTO> findAll();

}
