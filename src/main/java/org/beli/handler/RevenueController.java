package org.beli.handler;

import org.beli.dtos.req.CreateRevenueRequestDto;
import org.beli.dtos.req.UpdateProductRequestDto;
import org.beli.dtos.req.UpdateRevenueRequestDto;
import org.beli.dtos.res.RevenueResponseDto;
import org.beli.entities.Product;
import org.beli.entities.Revenues;
import org.beli.services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/revenue")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;

    @PostMapping
    public RevenueResponseDto createFee(@RequestBody CreateRevenueRequestDto revenue) {
        return revenueService.mappingToRevenueResponse(revenueService.createNewRevenue(revenue));
    }


    @GetMapping
    public List<RevenueResponseDto> getAll() {
        var revenues = revenueService.findAll();

        return revenues.stream()
                .map(revenue -> revenueService.mappingToRevenueResponse(revenue))
                .filter(Objects::nonNull)
                .toList();
    }

    @DeleteMapping("/{id}")
    public boolean deleteRevenue(@PathVariable("id") String id) {
        revenueService.deleteById(id);
        return true;
    }

    @PatchMapping
    public RevenueResponseDto updateRevenues(@RequestBody UpdateRevenueRequestDto dto) {
        return revenueService.mappingToRevenueResponse(revenueService.updateRevenue(dto));
    }
}
