package org.beli.repositories;

import org.beli.entities.Fees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeRepository extends JpaRepository<Fees, String> {

    Optional<List<Fees>> findByFeePlatform(String feePlatform);
}
