package com.kevinqiu.lotterysystem.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageListDTO<T> {

    private Integer prizeTotalCount;

    private List<T> pageList;

}
