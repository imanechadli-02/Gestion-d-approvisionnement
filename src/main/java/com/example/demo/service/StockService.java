package com.example.demo.service;

import com.example.demo.dto.stock.RequestStockDTO;
import com.example.demo.dto.stock.ResponseStockDTO;

public interface StockService {
    ResponseStockDTO createStock(RequestStockDTO requestDTO);
    ResponseStockDTO updateStock(RequestStockDTO requestDTO);
}
