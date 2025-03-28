package org.beli.handler;

import org.beli.dtos.ResponseDto;
import org.beli.dtos.req.IncomePayload;
import org.beli.entities.Income;
import org.beli.enums.IncomeFrom;
import org.beli.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public Income createIncome(@RequestBody IncomePayload income) {
        return incomeService.newIncome(income);
    }

    @GetMapping("/{from}")
    public List<Income> getIncomeByIncomeFrom(@PathVariable("from") String from) {
        return incomeService.findAllByIncomeFrom(from);
    }

    @DeleteMapping("/{id}")
    public void deleteIncome(@PathVariable("id") String id) {
        incomeService.deleteIncome(id);
    }

    @GetMapping("/{from}/total")
    public ResponseEntity<?> getTotalIncomeByIncomeFrom(@PathVariable("from") String from) {
        var incomes = incomeService.findAllByIncomeFrom(from);
        var total = incomes.stream().mapToDouble(e -> {
            return Double.parseDouble(e.getAmount());
        }).sum();
        var decimals = new BigDecimal(total);
        return ResponseEntity.ok(new ResponseDto<String>(HttpStatus.OK, "success", decimals.toString()));
    }
}
