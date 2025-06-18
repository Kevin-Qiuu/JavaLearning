package com.kevinqiu.lotterysystem.service.impl;

import cn.hutool.core.lang.UUID;
import com.kevinqiu.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    // @Value 通过表达式（静态值、配置文件、环境变量、SpEL等）将外部值注入到 Spring 管理的 Bean 中，实现配置与代码解耦。
    @Value("${pic.local-path}")
    private String picSavePath;

    @Override
    public String savePicture(MultipartFile imgFile) {

        // 获取目录对象
        File picSaveDir = new File(picSavePath);
        if (!picSaveDir.exists()) {
            if (!picSaveDir.mkdirs()) {
                throw new ServiceException(ServiceErrorCodeConstants.MAKE_DIR_ERROR);
            }
        }

        // 创建图像本地存储索引
        // 获取图像后缀
        String imgName = imgFile.getOriginalFilename();
        if (!StringUtils.hasLength(imgName)) {
            throw new ServiceException(ServiceErrorCodeConstants.ORIGINAL_FILE_ERROR);
        }
        String suffix = imgName.substring(imgName.lastIndexOf("."));
        // 创建图像唯一索引
        String prefix = UUID.randomUUID().toString();
        String imgIndex = prefix + suffix;

        // 存储图像
        try {
            imgFile.transferTo(new File(picSavePath + "/" + imgIndex));
        } catch (Exception e) {
            log.error("文件存储异常，e: ", e);
            throw new ServiceException(ServiceErrorCodeConstants.FILE_SAVE_ERROR);
        }

        return imgIndex;
    }
}
