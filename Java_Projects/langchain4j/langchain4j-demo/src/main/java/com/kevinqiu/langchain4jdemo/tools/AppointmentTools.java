package com.kevinqiu.langchain4jdemo.tools;

import com.kevinqiu.langchain4jdemo.entity.Appointment;
import com.kevinqiu.langchain4jdemo.service.AppointmentService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppointmentTools {

    @Autowired
    private AppointmentService appointmentService;

    @Tool(name = "预约挂号", value = "根据参数，先执行工具方法queryDepartment查询是否可预约，" +
            "并直接给用户回答是否可预约，并让用户确认所有预约信息，只有用户明确表达确认后才可以进行预约。" +
            "如果用户没有提供具体的医生姓名，请从向量存储中找到一位医生。")
    public String bookAppointment(Appointment appointment) {
        log.info("预约挂号，bookAppointment");
        // 在数据库中查找是否已经存在了对应的预约记录
        Appointment appointmentDB = appointmentService.getOne(appointment);
        if (appointmentDB == null) {
            appointmentDB = new Appointment();
            appointmentDB.setId(null); // 防止大模型幻觉设置了id
            if (appointmentService.save(appointment)) {
                return "当前用户预约成功，向用户再次返回预约详情";
            } else {
                return "当前用户预约失败";
            }
        }

        return "当前用户在相同的科室和时间已有预约";

    }

    @Tool (name = "取消预约挂号", value = "根据用户提供的预约参数，查询预约是否存在，如果存在则删除预约记录并返回取消预约成功，否则返回取消预约失败")
    public String cancelAppointment (Appointment appointment) {
        Appointment appointmentDB = appointmentService.getOne(appointment);
        if (appointment != null) {
            if (appointmentService.removeById(appointment.getId())){
                return "取消预约成功";
            } else {
                return "取消预约失败";
            }
        }
        return "不存在这个预约记录，请重新核对参数";
    }


    @Tool(name = "查询是否有号源", value = "根据用户提供的科室名称，日期（年月日），时间（上午/下午）" +
            "和医生（姓名）这四个变量查询是否有号源，并返回给用户")
    public boolean queryDepartment(
            @P(value = "科室名称") String name,
            @P(value = "日期（年月日）") String date,
            @P(value = "时间，可选值：上午 or 下午") String time,
            @P(value = "医生姓名") String doctor) {
        log.info("查询是否有号源：queryDepartment");
        log.info("科室名称 String name: {}", name);
        log.info("日期（年月日） String date: {}", date);
        log.info("时间 String time: {}", time);
        log.info("医生姓名 String doctor: {}", doctor);

        // todo
        // 查询是否有可以预约的医生

        // 查询是否有号源，需要查询医生的排班时间段以及排班时间段内是否已经约满

        return true;
    }

}
