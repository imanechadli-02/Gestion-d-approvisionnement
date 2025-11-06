package com.example.demo.dto.mouvemantStock;


import com.example.demo.entity.Stock;
import com.example.demo.entity.enums.TypeMouvement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class ResponseMouvementStockDTO {
    private Long id;
    private Stock stock;
    private Integer quantite;
    private TypeMouvement typeMouvement;
    private LocalDateTime dateMouvement;
}
