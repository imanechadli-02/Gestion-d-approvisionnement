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

    @GetMapping
    public ResponseEntity<List<ResponseBonSortieDTO>> getAllBonsDeSortie() {
        List<ResponseBonSortieDTO> bons = bonSortieService.findAllBonsDeSortie();
        return ResponseEntity.ok(bons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBonSortieDTO> getBonDeSortieById(@PathVariable Long id) {
        ResponseBonSortieDTO bon = bonSortieService.findBonDeSortieById(id);
        return ResponseEntity.ok(bon);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseBonSortieDTO> updateBonDeSortie(@PathVariable Long id,
                                                                  @RequestBody RequestBonSortieDTO requestDTO) {
        ResponseBonSortieDTO updatedBon = bonSortieService.updateBonDeSortie(id, requestDTO);
        return ResponseEntity.ok(updatedBon);
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<Void> validerBonDeSortie(@PathVariable Long id) {
        bonSortieService.validerBonDeSortie(id);
        return ResponseEntity.noContent().build();
    }

}
