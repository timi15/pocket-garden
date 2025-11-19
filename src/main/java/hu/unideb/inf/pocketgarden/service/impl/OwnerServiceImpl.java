package hu.unideb.inf.pocketgarden.service.impl;

import hu.unideb.inf.pocketgarden.data.entity.OwnerEntity;
import hu.unideb.inf.pocketgarden.data.repository.OwnerRepository;
import hu.unideb.inf.pocketgarden.exception.AlreadyExistsException;
import hu.unideb.inf.pocketgarden.exception.NotFoundException;
import hu.unideb.inf.pocketgarden.mapper.OwnerMapper;
import hu.unideb.inf.pocketgarden.service.OwnerService;
import hu.unideb.inf.pocketgarden.service.dto.request.OwnerReqDto;
import hu.unideb.inf.pocketgarden.service.dto.response.OwnerResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    @Override
    public OwnerResDto save(OwnerReqDto ownerReqDto) {

        if (ownerRepository.existsByUsername(ownerReqDto.getUsername())) {
            throw new AlreadyExistsException(
                    "Owner already exists with this username: "
                            + ownerReqDto.getUsername()
            );
        }

        OwnerEntity owner = ownerMapper.toEntity(ownerReqDto);
        return ownerMapper.toResponseDto(ownerRepository.save(owner));
    }

    @Override
    public OwnerResDto findById(UUID id) {
        if (!ownerRepository.existsById(id)) {
            throw new NotFoundException("Owner not found with id: " + id);
        }
        return ownerMapper.toResponseDto(ownerRepository.findById(id).get());
    }

    @Override
    public List<OwnerResDto> findAll() {
        List<OwnerEntity> ownerEntities = ownerRepository.findAll();
        return ownerMapper.map(ownerEntities);
    }
}
