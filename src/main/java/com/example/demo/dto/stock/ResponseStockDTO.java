package com.example.demo.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseStockDTO {
    private Long id;
    private String numeroLot;
    private LocalDate dateEntree;
    private Integer quantite;
    private Double prixAchatUnitaire;
    private Long commandeId;
    private Long produitId;
}
