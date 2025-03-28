package org.beli.services;

import org.beli.dtos.req.IncomePayload;
import org.beli.entities.Income;
import org.beli.enums.IncomeFrom;
import org.beli.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public Income newIncome(IncomePayload payload) {

        Income income = new Income();
        income.setAmount(payload.amount());
        income.setIncomeFrom(payload.from());
        income.setMonth(payload.month());
        income.setYear(payload.year());
        income.setNote(payload.note());

        return incomeRepository.save(income);
    }

    public List<Income> findAllByIncomeFrom(String from) {
        return incomeRepository.findByIncomeFrom(from);
    }

    public void deleteIncome(String id) {
        incomeRepository.deleteById(id);
    }
}
