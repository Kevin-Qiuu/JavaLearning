package com.kevinqiu.springmvc_demos.mapper;

import com.kevinqiu.springmvc_demos.model.MessageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageInfoMapper {
    List<MessageInfo> selectAllMessage();

    Integer insertMessage(MessageInfo messageInfo);
}
