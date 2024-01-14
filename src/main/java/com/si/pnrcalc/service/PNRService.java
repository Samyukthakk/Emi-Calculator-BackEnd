package com.si.pnrcalc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import com.si.pnrcalc.dto.ComputeDTO;
import com.si.pnrcalc.persistence.entity.Compute;
import com.si.pnrcalc.persistence.repository.ComputeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PNRService {

    @Autowired
    ComputeRepository repository;

    public ComputeDTO calculateSimpleInterest(ComputeDTO computeDTO) {
        log.info(computeDTO.toString());
        computeDTO.setResult(this.calculateEMI(computeDTO));
        Compute compute = new Compute(computeDTO);
        repository.save(compute);
        return computeDTO;
    }

    public List<ComputeDTO> getAllComputations() {
        return repository.findAll().stream().map(ComputeDTO::new).collect(Collectors.toList());
    }

    /**
     * Principle x ROI x [(1 plus ROI) pow of months]
     * Divided by [(1 plus ROI) pow of months] - 1
     * @param computeDTO
     * @return emi amount per month
     */
    private BigDecimal calculateEMI(ComputeDTO computeDTO) {
        // Yearly rate of interest in percentage is converted to monthly
        BigDecimal monthlyROI = computeDTO.getRoi()
                .divide(new BigDecimal(12), 2, RoundingMode.UP)
                .divide(new BigDecimal(100), 2, RoundingMode.UP);
        BigDecimal numerator = computeDTO.getPrinciple().multiply(monthlyROI).multiply(
                monthlyROI.add(BigDecimal.ONE).pow(computeDTO.getMonths().intValue()));
        BigDecimal denominator = monthlyROI.add(BigDecimal.ONE).pow(
                computeDTO.getMonths().intValue()).subtract(BigDecimal.ONE);
        return numerator.divide(denominator,2, RoundingMode.UP);
    }

}
