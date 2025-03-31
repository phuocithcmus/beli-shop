package org.beli.repositories;

import org.beli.entities.Revenues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends JpaRepository<Revenues, String> {
}
