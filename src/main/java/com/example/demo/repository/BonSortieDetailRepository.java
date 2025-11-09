package com.example.demo.repository;

import com.example.demo.entity.BonSortieDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonSortieDetailRepository extends JpaRepository<BonSortieDetail, Long> {

    List<BonSortieDetail> findByBonSortieId(Long bonSortieId);
}
