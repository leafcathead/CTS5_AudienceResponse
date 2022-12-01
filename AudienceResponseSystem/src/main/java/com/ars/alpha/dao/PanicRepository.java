package com.ars.alpha.dao;

import com.ars.alpha.model.PanicResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanicRepository extends JpaRepository<PanicResponse, Long> {
}
