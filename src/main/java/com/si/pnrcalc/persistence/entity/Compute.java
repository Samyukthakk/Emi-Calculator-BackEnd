package com.si.pnrcalc.persistence.entity;

import java.math.BigDecimal;

import com.si.pnrcalc.dto.ComputeDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "compute")
public class Compute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private BigDecimal principal;

    @Column(name = "roi")
    private BigDecimal rateOfInterest;

    @Column
    private BigDecimal months;

    @Column
    private String email;

    @Column
    private BigDecimal emi;

    public Compute(ComputeDTO dto) {
        this.months = dto.getMonths();
        this.principal = dto.getPrinciple();
        this.rateOfInterest = dto.getRoi();
        this.email = dto.getEmail();
        this.emi = dto.getResult();
    }

}
