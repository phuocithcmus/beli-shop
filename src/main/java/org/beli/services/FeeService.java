package org.beli.services;

import org.beli.dtos.req.CreateFeeRequestDto;
import org.beli.dtos.req.PhaseRequestDto;
import org.beli.entities.Fees;
import org.beli.entities.Phase;
import org.beli.repositories.FeeRepository;
import org.beli.repositories.PhaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeeService extends BaseService<Fees, String> {

    @Autowired
    private FeeRepository feeRepository;

    public FeeService(FeeRepository feeRepository) {
        super(feeRepository);
    }

    public Fees mappingToCreateEntity(CreateFeeRequestDto dto) {
        var fee = new Fees();
        fee.setFeeType(dto.feeType());
        fee.setFeePlatform(dto.feePlatform());
        fee.setFeeAmount(dto.feeAmount());
        fee.setCreatedAt(System.currentTimeMillis());
        fee.setUpdatedAt(System.currentTimeMillis());
        return fee;
    }

    public Optional<List<Fees>> findByFeePlatform(String feePlatform) {
        return feeRepository.findByFeePlatform(feePlatform);
    }
}
