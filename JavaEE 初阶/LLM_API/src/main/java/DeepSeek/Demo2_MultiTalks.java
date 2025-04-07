package DeepSeek;

// dashscope SDK的版本 >= 2.18.2
import java.util.Arrays;
import java.lang.System;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;

public class Demo2_MultiTalks {
    public static GenerationResult callWithMessage() throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        Message userMsg1 = Message.builder()
                .role(Role.USER.getValue())
                .content("9.9 与 9.11 哪一个数字更大？")
                .build();
        Message AssistantMsg = Message.builder()
                .role(Role.ASSISTANT.getValue())
                .content("9.9 与 9.11 相减为正数，因此 9.9 大。")
                .build();
        Message UserMsg2 = Message.builder()
                .role(Role.USER.getValue())
                .content("我刚才问了你什么问题，你的回答是什么？")
                .build();
        GenerationParam param = GenerationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                .apiKey("sk-d77a9ebd93c943be8bae5158585871f0")
                .model("deepseek-r1")
                .messages(Arrays.asList(userMsg1,AssistantMsg,UserMsg2))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        return gen.call(param);
    }
    public static void main(String[] args) {
        try {
            GenerationResult result = callWithMessage();
            System.out.println("思考过程：");
            System.out.println(result.getOutput().getChoices().get(0).getMessage().getReasoningContent());
            System.out.println("回复内容：");
            System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent());
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            // 使用日志框架记录异常信息
            System.err.println("An error occurred while calling the generation service: " + e.getMessage());
        }
        System.exit(0);
    }
}