package com.si.pnrcalc.api.controller;

import java.util.List;

import com.si.pnrcalc.dto.ComputeDTO;
import com.si.pnrcalc.service.PNRService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/pnr")
@CrossOrigin
public class PNRController {

    @Autowired
    PNRService pnrService;

//    @RequestMapping("/si")
    @PostMapping
    public ComputeDTO calculateSI(@Valid @RequestBody ComputeDTO computeDTO) {
        return pnrService.calculateSimpleInterest(computeDTO);
    }

    @GetMapping
    public List<ComputeDTO> getComputations() {
        return pnrService.getAllComputations();
    }
}
