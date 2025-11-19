package hu.unideb.inf.pocketgarden.service;

import hu.unideb.inf.pocketgarden.service.dto.request.OwnerReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.OwnerResDto;

import java.util.List;
import java.util.UUID;

public interface OwnerService {

    OwnerResDto save(OwnerReqDto ownerReqDto);

    OwnerResDto findById(UUID id);

    List<OwnerResDto> findAll();

}
