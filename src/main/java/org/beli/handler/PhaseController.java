package org.beli.handler;

import org.beli.dtos.req.PhaseRequestDto;
import org.beli.entities.Phase;
import org.beli.services.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/phase")
public class PhaseController {

    @Autowired
    private PhaseService phaseService;

    @PostMapping
    public Phase createPhase(@RequestBody PhaseRequestDto phase) {
        return phaseService.save(phaseService.mappingToCreateEntity(phase));
    }

    @GetMapping
    public List<Phase> getAll() {
        return phaseService.findAll();
    }
}
