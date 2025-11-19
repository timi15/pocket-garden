package hu.unideb.inf.pocketgarden.data.repository;

import hu.unideb.inf.pocketgarden.data.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity, UUID> {

    boolean existsByUsername(String username);
}
