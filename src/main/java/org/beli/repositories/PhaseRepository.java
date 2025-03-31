package org.beli.repositories;

import org.beli.entities.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, String> {
    // if you need to find a phase by its name, you can uncomment the following method
}
