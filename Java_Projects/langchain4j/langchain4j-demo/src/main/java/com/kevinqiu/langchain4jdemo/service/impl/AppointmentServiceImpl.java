package com.kevinqiu.langchain4jdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevinqiu.langchain4jdemo.entity.Appointment;
import com.kevinqiu.langchain4jdemo.mapper.AppointmentMapper;
import com.kevinqiu.langchain4jdemo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment>
        implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public Appointment getOne(Appointment appointment) {

        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Appointment::getUsername, appointment.getUsername());
        queryWrapper.eq(Appointment::getIdCard, appointment.getIdCard());
        queryWrapper.eq(Appointment::getDepartment, appointment.getDepartment());
        queryWrapper.eq(Appointment::getDate, appointment.getDate());
        queryWrapper.eq(Appointment::getTime, appointment.getTime());

        return appointmentMapper.selectOne(queryWrapper);
    }
}
