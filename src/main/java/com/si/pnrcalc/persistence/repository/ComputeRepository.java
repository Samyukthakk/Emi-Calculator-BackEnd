package com.si.pnrcalc.persistence.repository;

import com.si.pnrcalc.persistence.entity.Compute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputeRepository extends JpaRepository<Compute, Long> {

}
