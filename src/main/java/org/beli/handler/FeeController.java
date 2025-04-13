package org.beli.handler;

import org.beli.dtos.req.CreateFeeRequestDto;
import org.beli.dtos.req.PhaseRequestDto;
import org.beli.dtos.req.UpdateFeeRequestDto;
import org.beli.entities.Fees;
import org.beli.entities.Phase;
import org.beli.services.FeeService;
import org.beli.services.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fee")
public class FeeController {
    @Autowired
    private FeeService feeService;

    @PostMapping
    public Fees createFee(@RequestBody CreateFeeRequestDto fee) {
        return feeService.save(feeService.mappingToCreateEntity(fee));
    }


    @PatchMapping
    public Fees updateFee(@RequestBody UpdateFeeRequestDto fee) {
        return feeService.update(feeService.mappingToUpdateEntity(fee));
    }

    @GetMapping
    public List<Fees> getAll() {
        return feeService.findAll();
    }
}
