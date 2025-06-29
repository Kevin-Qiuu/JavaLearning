package com.kevinqiu.langchain4jdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevinqiu.langchain4jdemo.entity.Appointment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
}
