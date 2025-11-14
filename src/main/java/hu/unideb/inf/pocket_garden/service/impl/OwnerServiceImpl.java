package hu.unideb.inf.pocket_garden.service.impl;

import hu.unideb.inf.pocket_garden.data.entity.OwnerEntity;
import hu.unideb.inf.pocket_garden.data.repository.OwnerRepository;
import hu.unideb.inf.pocket_garden.exception.AlreadyExistsException;
import hu.unideb.inf.pocket_garden.exception.NotFoundException;
import hu.unideb.inf.pocket_garden.mapper.OwnerMapper;
import hu.unideb.inf.pocket_garden.service.OwnerService;
import hu.unideb.inf.pocket_garden.service.dto.request.OwnerReqDTO;
import hu.unideb.inf.pocket_garden.service.dto.response.OwnerResDTO;
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
    public OwnerResDTO save(OwnerReqDTO ownerReqDTO) {

        if (ownerRepository.existsByUsername(ownerReqDTO.getUsername())) {
            throw new AlreadyExistsException(
                    "Owner already exists with this username: "
                            + ownerReqDTO.getUsername()
            );
        }

        OwnerEntity owner = ownerMapper.toEntity(ownerReqDTO);
        return ownerMapper.toResponseDTO(ownerRepository.save(owner));
    }

    @Override
    public OwnerResDTO findById(UUID id) {
        if (!ownerRepository.existsById(id)) {
            throw new NotFoundException("Owner not found with id: " + id);
        }
        return ownerMapper.toResponseDTO(ownerRepository.findById(id).get());
    }

    @Override
    public List<OwnerResDTO> findAll() {
        List<OwnerEntity> ownerEntities = ownerRepository.findAll();
        return ownerMapper.map(ownerEntities);
    }
}
