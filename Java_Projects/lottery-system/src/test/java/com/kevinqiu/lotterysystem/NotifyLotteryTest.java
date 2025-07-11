package com.kevinqiu.lotterysystem;

import cn.hutool.core.date.DateUtil;
import com.kevinqiu.lotterysystem.common.utils.MailUtil;
import com.kevinqiu.lotterysystem.common.utils.SMSUtil;
import com.kevinqiu.lotterysystem.dao.dataobject.WinningRecordDO;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeTiersEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class NotifyLotteryTest {

    @Autowired
    private MailUtil mailUtil;

    @Test
    public void sendMail() {
        String subject = "这是一封测试邮件～～";
        String context = "这就是来自 KevinQiu 的一封测试邮件～～";
        String to = "qiujunhang@my.swjtu.edu.cn";
        mailUtil.sendSimpleMail(to, subject, context);
    }

    @Test
    public void sendMessage() {

        WinningRecordDO winningRecordDO = new WinningRecordDO();
        winningRecordDO.setWinnerName("陈多多");
        winningRecordDO.setActivityName("KevinQiu 的测试活动");
        winningRecordDO.setPrizeTier(ActivityPrizeTiersEnum.FIRST_PRIZE.name());
        winningRecordDO.setPrizeName("山姆 500 元购物卡");

        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("name", winningRecordDO.getWinnerName());
        messageMap.put("activityName", winningRecordDO.getActivityName());
        messageMap.put("prizeTiers", ActivityPrizeTiersEnum.forName(winningRecordDO.getPrizeTier()).getMessage());
        messageMap.put("prizeName", winningRecordDO.getPrizeName());
        messageMap.put("winningTime", DateUtil.formatTime(new Date()));

//        SMSUtil.sendVerificationCodeBySpug()

    }

}
