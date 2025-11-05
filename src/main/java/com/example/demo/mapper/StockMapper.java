package com.example.demo.mapper;


import com.example.demo.dto.stock.RequestStockDTO;
import com.example.demo.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StockMapper {
    RequestStockDTO toRequestStockDTO(Stock stock);
    Stock toStock(RequestStockDTO requestStockDTO);
    void updateStock(RequestStockDTO stockDTO, @MappingTarget Stock stock);
}
