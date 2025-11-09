package com.example.demo.service;

import com.example.demo.dto.bonSortie.RequestBonSortieDTO;
import com.example.demo.dto.bonSortie.ResponseBonSortieDTO;

import java.util.List;

public interface BonSortieService {
    ResponseBonSortieDTO createBonDeSortie(RequestBonSortieDTO requestDTO);
    List<ResponseBonSortieDTO> findAllBonsDeSortie();
    ResponseBonSortieDTO findBonDeSortieById(Long id);
    ResponseBonSortieDTO updateBonDeSortie(Long id, RequestBonSortieDTO requestDTO);
    void deleteBonDeSortie(Long id);
    void validerBonDeSortie(Long id);
    void annulerBonDeSortie(Long id);
    List<ResponseBonSortieDTO> findBonsByAtelier(String atelier);
}
