package org.beli.services;

import org.beli.dtos.req.PhaseRequestDto;
import org.beli.entities.Phase;
import org.beli.repositories.PhaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhaseService extends BaseService<Phase, String> {
    @Autowired
    private PhaseRepository phaseRepository;

    public PhaseService(PhaseRepository phaseRepository) {
        super(phaseRepository);
    }

    public Phase mappingToCreateEntity(PhaseRequestDto dto) {
        var phase = new Phase();
        phase.setPhaseCode(dto.phaseCode());
        phase.setPhaseName(dto.phaseName());
        phase.setCreatedAt(System.currentTimeMillis());
        phase.setUpdatedAt(System.currentTimeMillis());
        return phase;
    }

}
