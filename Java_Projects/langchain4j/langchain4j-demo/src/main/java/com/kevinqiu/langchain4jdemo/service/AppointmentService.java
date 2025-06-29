package com.kevinqiu.langchain4jdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kevinqiu.langchain4jdemo.entity.Appointment;

public interface AppointmentService extends IService<Appointment> {

    Appointment getOne(Appointment appointment);

}
