package org.beli.handler;

import org.beli.dtos.req.CreateRevenueRequestDto;
import org.beli.dtos.res.RevenueResponseDto;
import org.beli.entities.Revenues;
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
    public RevenueResponseDto createFee(@RequestBody CreateRevenueRequestDto revenue) {
        return revenueService.mappingToRevenueResponse(revenueService.save(revenueService.mappingToCreateEntity(revenue)));
    }

    @GetMapping
    public List<RevenueResponseDto> getAll() {
        var revenues = revenueService.findAll();

        return revenues.stream()
                .map(revenue -> revenueService.mappingToRevenueResponse(revenue))
                .toList();
    }
}
