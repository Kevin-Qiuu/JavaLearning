package com.kevinqiu.lotterysystem.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 保存图片文件
     * @param picFile
     * @return
     */
    String savePicture(MultipartFile picFile);





}
