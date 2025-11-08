package com.example.demo.dto.bonSortie;

import com.example.demo.dto.bonSortieDetail.ResponseBonSortieDetailDTO;
import com.example.demo.entity.enums.StatutBon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBonSortieDTO {

    private Long id;
    private String numeroBon;
    private LocalDate dateSortie;
    private String atelier;
    private String motif;
    private StatutBon statut;
    private List<ResponseBonSortieDetailDTO> details;
}
