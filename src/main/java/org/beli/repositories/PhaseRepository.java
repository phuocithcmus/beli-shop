package org.beli.repositories;

import org.beli.entities.Income;
import org.beli.entities.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, String> {
    // if you need to find a phase by its name, you can uncomment the following method

    Optional<Phase> findByPhaseCode(String phaseCode);
}
