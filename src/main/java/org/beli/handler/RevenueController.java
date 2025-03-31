package org.beli.handler;

import org.beli.dtos.req.CreateFeeRequestDto;
import org.beli.dtos.req.CreateRevenueRequestDto;
import org.beli.entities.Fees;
import org.beli.entities.Revenues;
import org.beli.services.FeeService;
import org.beli.services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/revenue")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;

    @PostMapping
    public Revenues createFee(@RequestBody CreateRevenueRequestDto revenue) {
        return revenueService.save(revenueService.mappingToCreateEntity(revenue));
    }

    @GetMapping
    public List<Revenues> getAll() {
        return revenueService.findAll();
    }
}
