package com.kevinqiu.lotterysystem.controller;

import com.kevinqiu.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ControllerException;
import com.kevinqiu.lotterysystem.common.pojo.CommonResult;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.controller.param.PageParam;
import com.kevinqiu.lotterysystem.controller.param.PrizeCreateParam;
import com.kevinqiu.lotterysystem.controller.result.PrizeInfoListResult;
import com.kevinqiu.lotterysystem.controller.result.PrizeListPageResult;
import com.kevinqiu.lotterysystem.service.FileService;
import com.kevinqiu.lotterysystem.service.PrizeService;
import com.kevinqiu.lotterysystem.service.dto.PageListDTO;
import com.kevinqiu.lotterysystem.service.dto.PrizeInfoDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
public class PrizeController {

    @Resource(name = "prizeServiceImpl")
    private PrizeService prizeService;

    @Resource(name = "fileServiceImpl")
    private FileService fileService;

    /**
     * 创建活动
     * @param param String 类型，PrizeCreateParam 序列化 Json 字符串
     * @param picFile MultipartFile
     * @return CommonResult
     * @see PrizeCreateParam
     */
    @RequestMapping("/prize/create")
    public CommonResult<Long> createPrize(@RequestParam("param") String param,
                                          @RequestParam("prizePic") MultipartFile picFile) {
        log.info("createPrize CreatePrizeParam:{}", param);
        PrizeCreateParam prizeCreateParam = JacksonUtil.readValue(param, PrizeCreateParam.class);
        return CommonResult.success(
                prizeService.createPrize(prizeCreateParam, picFile));
    }


    @RequestMapping("/prize/find-list")
    public CommonResult<PrizeListPageResult> findPrizeList(PageParam param) {
        log.info("findPrizeList -> param: {}", JacksonUtil.writeValueAsString(param));
        PageListDTO<PrizeInfoDTO> pageListDTO = prizeService.findPrizeList(param);
        return CommonResult.success(convertToPrizeListPageResult(pageListDTO));
    }

    @RequestMapping("prize/find-all")
    public CommonResult<PrizeInfoListResult> findALlPrizeInfo() {
        log.info("findAllPrizeInfo");
        List<PrizeInfoDTO> prizeInfoDTOList = prizeService.findAllPrize();
        return CommonResult.success(convertToPrizeInfoListResult(prizeInfoDTOList));
    }

    private PrizeInfoListResult convertToPrizeInfoListResult(List<PrizeInfoDTO> prizeInfoDTOList) {
        if (null == prizeInfoDTOList) {
            throw new ControllerException(ControllerErrorCodeConstants.PRIZE_INFO_IS_NULL);
        }
        PrizeInfoListResult prizeInfoListResult = new PrizeInfoListResult();
        List<PrizeInfoListResult.PrizeInfo> prizeInfoList = prizeInfoDTOList.stream()
                .map(prizeInfoDTO -> {
                    PrizeInfoListResult.PrizeInfo prizeInfo = new PrizeInfoListResult.PrizeInfo();
                    prizeInfo.setPrizeId(prizeInfoDTO.getPrizeId());
                    prizeInfo.setPrizeName(prizeInfoDTO.getPrizeName());
                    prizeInfo.setDescription(prizeInfoDTO.getDescription());
                    prizeInfo.setImageUrl(prizeInfoDTO.getImageUrl());
                    prizeInfo.setPrice(prizeInfoDTO.getPrice());
                    return prizeInfo;
                }).toList();
        prizeInfoListResult.setPrizeInfoList(prizeInfoList);
        return prizeInfoListResult;
    }


    private PrizeListPageResult convertToPrizeListPageResult(PageListDTO<PrizeInfoDTO> pageListDTO){
        //todo 参数校验
        if (null == pageListDTO){
            throw new ControllerException(ControllerErrorCodeConstants.PAGE_LIST_IS_NULL);
        }
        PrizeListPageResult prizeListPageResult = new PrizeListPageResult();
        prizeListPageResult.setTotal(pageListDTO.getPrizeTotalCount());
        List<PrizeListPageResult.PrizeInfo> prizeList = pageListDTO.getPageList().stream().map(prizeInfoDTO -> {
            PrizeListPageResult.PrizeInfo prizeInfo = new PrizeListPageResult.PrizeInfo();
            prizeInfo.setPrizeId(prizeInfoDTO.getPrizeId());
            prizeInfo.setPrizeName(prizeInfoDTO.getPrizeName());
            prizeInfo.setPrice(prizeInfoDTO.getPrice());
            prizeInfo.setDescription(prizeInfoDTO.getDescription());
            prizeInfo.setImageUrl(prizeInfoDTO.getImageUrl());
            return prizeInfo;
        }).toList();
        prizeListPageResult.setPrizeInfoList(prizeList);
        return prizeListPageResult;
    }

}
