package com.si.pnrcalc.dto;

import java.math.BigDecimal;

import com.si.pnrcalc.persistence.entity.Compute;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
public class ComputeDTO {

    @NotNull(message = "Principle amount is mandatory to calculate emi")
    @Range(min = 0, message = "Principle should be a positive number")
    private BigDecimal principle;
    @NotNull(message = "ROI is mandatory to calculate emi")
    @Range(min = 0, max = 100, message = "ROI should be between 0 to 100")
    private BigDecimal roi;
    @NotNull(message = "Number of months to calculate emi")
    @Range(min = 1, max = 360, message = "Months should be between 1 to 30")
    private BigDecimal months;
    private BigDecimal result;
    private String email;

    public ComputeDTO(Compute compute) {
        this.months = compute.getMonths();
        this.principle = compute.getPrincipal();
        this.result = compute.getEmi();
        this.roi = compute.getRateOfInterest();
        this.email = compute.getEmail();
    }
}
