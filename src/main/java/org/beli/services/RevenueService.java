package org.beli.services;

import org.beli.dtos.req.CreateRevenueRequestDto;
import org.beli.entities.Revenues;
import org.beli.repositories.FeeRepository;
import org.beli.repositories.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevenueService extends BaseService<Revenues, String> {
    @Autowired
    private RevenueRepository revenueRepository;

    public RevenueService(RevenueRepository revenueRepository) {
        super(revenueRepository);
    }

    public Revenues mappingToCreateEntity(CreateRevenueRequestDto dto) {
        Revenues revenues = new Revenues();
        revenues.setChannel(dto.channel());
        revenues.setPrice(dto.price());
        revenues.setSellPrice(dto.sellPrice());
        revenues.setRevenue(dto.revenue());
        revenues.setProductId(dto.productId());
        revenues.setAmount(dto.amount());
        revenues.setFees(dto.fees());
        revenues.setCreatedAt(System.currentTimeMillis());
        revenues.setUpdatedAt(System.currentTimeMillis());
        return revenues;
    }
}
