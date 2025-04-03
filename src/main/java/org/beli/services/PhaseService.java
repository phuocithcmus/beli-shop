package org.beli.services;

import org.beli.dtos.req.PhaseRequestDto;
import org.beli.entities.Phase;
import org.beli.repositories.PhaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

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

    public Optional<Phase> findByPhaseCode(String phaseCode) {
        return phaseRepository.findByPhaseCode(phaseCode);
    }

}
