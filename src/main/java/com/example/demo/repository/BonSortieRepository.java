package com.example.demo.repository;

import com.example.demo.entity.BonSortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonSortieRepository extends JpaRepository<BonSortie, Long> {

    BonSortie findByNumeroBon(String numeroBon);
    List<BonSortie> findByAtelier(String atelier);

}
