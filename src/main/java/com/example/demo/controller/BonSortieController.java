package com.example.demo.controller;

import com.example.demo.dto.bonSortie.RequestBonSortieDTO;
import com.example.demo.dto.bonSortie.ResponseBonSortieDTO;
import com.example.demo.service.BonSortieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bons-sortie")
@RequiredArgsConstructor
public class BonSortieController {

    private final BonSortieService bonSortieService;

    @PostMapping
    public ResponseEntity<ResponseBonSortieDTO> createBonDeSortie(@RequestBody RequestBonSortieDTO requestDTO) {
        ResponseBonSortieDTO createdBon = bonSortieService.createBonDeSortie(requestDTO);
        return ResponseEntity.ok(createdBon);
    }


}
