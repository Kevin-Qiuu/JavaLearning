package com.kevinqiu.lotterysystem.service;

import com.kevinqiu.lotterysystem.controller.param.PageParam;
import com.kevinqiu.lotterysystem.controller.param.PrizeCreateParam;
import com.kevinqiu.lotterysystem.service.dto.PageListDTO;
import com.kevinqiu.lotterysystem.service.dto.PrizeInfoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PrizeService {
    /**
     * 创建奖品
     * @param param PrizeCreateParam
     * @see PrizeCreateParam
     * @param picFile MultipartFile
     * @return Long
     */
    Long createPrize(PrizeCreateParam param, MultipartFile picFile);

    PageListDTO<PrizeInfoDTO> findPrizeList(PageParam param);

    List<PrizeInfoDTO> findAllPrize();
}
