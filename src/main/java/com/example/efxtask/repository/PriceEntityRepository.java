package com.example.efxtask.repository;

import com.example.efxtask.model.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceEntityRepository extends JpaRepository<Price, Long> {
}
