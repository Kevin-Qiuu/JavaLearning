package com.kevinqiu.lotterysystem.service.impl;

import com.kevinqiu.lotterysystem.controller.param.PageParam;
import com.kevinqiu.lotterysystem.controller.param.PrizeCreateParam;
import com.kevinqiu.lotterysystem.dao.dataobject.PrizeInfoDO;
import com.kevinqiu.lotterysystem.dao.mapper.PrizeMapper;
import com.kevinqiu.lotterysystem.service.FileService;
import com.kevinqiu.lotterysystem.service.PrizeService;
import com.kevinqiu.lotterysystem.service.dto.PageListDTO;
import com.kevinqiu.lotterysystem.service.dto.PrizeInfoDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PrizeServiceImpl implements PrizeService {

    @Resource(name = "fileServiceImpl")
    private FileService fileService;

    @Resource(name = "prizeMapper")
    private PrizeMapper prizeMapper;

    @Override
    public Long createPrize(PrizeCreateParam param, MultipartFile picFile) {
        // 创建 DAO 请求
        PrizeInfoDO prizeInfoDO = new PrizeInfoDO();
        prizeInfoDO.setName(param.getPrizeName());
        prizeInfoDO.setDescription(param.getDescription());
        prizeInfoDO.setPrice(param.getPrice());
        // 存储奖品图像
        prizeInfoDO.setImageUrl(fileService.savePicture(picFile));

        // 发送数据库存储请求
        prizeMapper.createPrize(prizeInfoDO);

        return prizeInfoDO.getId();
    }

    @Override
    public PageListDTO<PrizeInfoDTO> findPrizeList(PageParam param) {
        Integer prizeTotalCount = prizeMapper.selectPrizeCount();

        List<PrizeInfoDO> pageListDO
                = prizeMapper.selectPrizePageList(param.offset(), param.getPageSize());

        List<PrizeInfoDTO> pageList = pageListDO.stream().map(prizeInfoDO -> {
            PrizeInfoDTO prizeInfoDTO = new PrizeInfoDTO();
            prizeInfoDTO.setPrizeId(prizeInfoDO.getId());
            prizeInfoDTO.setPrizeName(prizeInfoDO.getName());
            prizeInfoDTO.setPrice(prizeInfoDO.getPrice());
            prizeInfoDTO.setDescription(prizeInfoDO.getDescription());
            prizeInfoDTO.setImageUrl(prizeInfoDO.getImageUrl());
            return prizeInfoDTO;
        }).toList();

        return new PageListDTO<>(prizeTotalCount, pageList);
    }
}
