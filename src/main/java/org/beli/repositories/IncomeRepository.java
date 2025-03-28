package org.beli.repositories;

import org.beli.entities.Income;
import org.beli.enums.IncomeFrom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, String> {
    List<Income> findByIncomeFrom(String from);
}
