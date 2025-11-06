package com.example.demo.service.impl;

import com.example.demo.dto.stock.RequestStockDTO;
import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.Stock;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.mapper.StockMapper;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.StockService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    @Override
    public ResponseStockDTO createStock(RequestStockDTO requestDTO) {
        List<String> errors = new ArrayList<>();
        if(stockRepository.existsStockByNumeroLot(requestDTO.getNumeroLot())){
            errors.add("Le numero de stock déja existe essayer avec une autre valeur");
        }
        if(!errors.isEmpty()){
            throw new DuplicateResourceException(errors);
        }

        Stock stock = stockMapper.toStock(requestDTO);
        Stock saved=stockRepository.save(stock);
        return stockMapper.toResponseStockDTO(saved);
    }

    @Override
    public ResponseStockDTO updateStock(RequestStockDTO requestDTO) {
        return null;
    }
}
