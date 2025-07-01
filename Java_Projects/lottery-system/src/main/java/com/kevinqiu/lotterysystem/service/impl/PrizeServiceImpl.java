package com.kevinqiu.lotterysystem.service.impl;

import com.kevinqiu.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ServiceException;
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

        List<PrizeInfoDTO> pageList = makePrizeInfoDTOList(pageListDO);

        return new PageListDTO<>(prizeTotalCount, pageList);
    }

    @Override
    public List<PrizeInfoDTO> findAllPrize() {
        List<PrizeInfoDO> prizeInfoDOList = prizeMapper.selectAll();
        return makePrizeInfoDTOList(prizeInfoDOList);
    }

    private List<PrizeInfoDTO> makePrizeInfoDTOList(List<PrizeInfoDO> prizeInfoDOList) {
        return prizeInfoDOList.stream()
                .map(prizeInfoDO -> {
                    PrizeInfoDTO prizeInfoDTO = new PrizeInfoDTO();
                    prizeInfoDTO.setPrizeId(prizeInfoDO.getId());
                    prizeInfoDTO.setPrizeName(prizeInfoDO.getName());
                    prizeInfoDTO.setPrice(prizeInfoDO.getPrice());
                    prizeInfoDTO.setDescription(prizeInfoDO.getDescription());
                    prizeInfoDTO.setImageUrl(prizeInfoDO.getImageUrl());
                    return prizeInfoDTO;
                }).toList();
    }

}
