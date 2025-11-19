package hu.unideb.inf.pocketgarden.data.repository;

import hu.unideb.inf.pocketgarden.data.entity.PlantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlantRepository extends JpaRepository<PlantEntity, UUID> {

    List<PlantEntity> findByOwnerId(UUID ownerId);

    boolean existsByNickname(String nickname);

}
