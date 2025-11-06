package com.example.demo.controller;
import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.Stock;
import com.example.demo.service.impl.StockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockServiceImpl stockService;

    @GetMapping
    public ResponseEntity<List<ResponseStockDTO>> getStock(){
        List<ResponseStockDTO> stocks = stockService.getAllStocks();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("produit/{id}")
    public ResponseEntity<List<ResponseStockDTO>> getStockByProduit(@PathVariable Long id){
        List<ResponseStockDTO> stoks=stockService.getStocksByProduit(id);
        return new ResponseEntity<>(stoks, HttpStatus.OK);
    }

}
