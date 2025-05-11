package com.kevinqiu.springBookDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResponse<T> {
    private Integer totalBooksCount = 0;
    private List<T> currentPageRecord;
    private PageRequest pageRequest;
}
