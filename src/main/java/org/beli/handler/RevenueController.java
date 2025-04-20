package org.beli.handler;

import org.beli.dtos.req.CreateRevenueRequestDto;
import org.beli.dtos.req.UpdateProductRequestDto;
import org.beli.dtos.req.UpdateRevenueRequestDto;
import org.beli.dtos.res.RevenueResponseDto;
import org.beli.entities.Product;
import org.beli.entities.Revenues;
import org.beli.services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        var revenues = revenueService.findAllSortBy("createdAt");

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

    @GetMapping(value = "/download_excel")
    public HttpEntity<ByteArrayResource> createExcelWithTaskConfigurations() throws IOException {
        try {
            ByteArrayOutputStream stream = revenueService.exportToExcel("products");
            ByteArrayResource resource = new ByteArrayResource(stream.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.xlsx");
            headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(stream.size())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
