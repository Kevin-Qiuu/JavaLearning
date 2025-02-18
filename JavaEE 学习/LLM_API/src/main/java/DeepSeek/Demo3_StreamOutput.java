package DeepSeek;
// dashscope SDK的版本 >= 2.18.2
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import io.reactivex.Flowable;
import java.lang.System;
import java.util.List;
import java.util.Scanner;

public class Demo3_StreamOutput {
    private static final Logger logger = LoggerFactory.getLogger(Demo3_StreamOutput.class);
    private static StringBuilder reasoningContent = new StringBuilder();
    private static StringBuilder finalContent = new StringBuilder();
    private static boolean isFirstPrint = true;

    private static void handleGenerationResult(GenerationResult message) {
        String reasoning = message.getOutput().getChoices().get(0).getMessage().getReasoningContent();
        String content = message.getOutput().getChoices().get(0).getMessage().getContent();

        if (!reasoning.isEmpty()) {
//            reasoningContent.append(reasoning);
            reasoningContent.replace(0, reasoningContent.length(), reasoning);
            if (isFirstPrint) {
                System.out.println("====================思考过程====================");
                isFirstPrint = false;
            }
            System.out.print(reasoning);
        }

        if (!content.isEmpty()) {
            finalContent.replace(0, finalContent.length(), content);
            if (!isFirstPrint) {
                System.out.println("\n====================完整回复====================");
                isFirstPrint = true;
            }
            System.out.print(content);
        }
    }
    private static GenerationParam buildGenerationParam(List<Message> userMsgs) {
        return GenerationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                .apiKey("sk-d77a9ebd93c943be8bae5158585871f0")
                .model("deepseek-r1")
                .messages(userMsgs)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .incrementalOutput(true)
                .build();
    }
    public static void streamCallWithMessage(Generation gen, List<Message> userMsgs)
            throws NoApiKeyException, ApiException, InputRequiredException {
        GenerationParam param = buildGenerationParam(userMsgs);
        Flowable<GenerationResult> result = gen.streamCall(param);
        result.blockingForEach(message -> handleGenerationResult(message));
    }

    public static void main(String[] args) {
        try {
            Generation gen = new Generation();
            Message userMsg1 = Message.builder()
                    .role(Role.USER.getValue())
                    .content("从现在开始，你是一只可爱的小狗。")
                    .build();
            Message AssistantMsg = Message.builder()
                    .role(Role.ASSISTANT.getValue())
                    .content("好的，我是一只小狗狗，汪汪汪～")
                    .build();
            ArrayList<Message> userMsgs = new ArrayList<>(Arrays.asList(userMsg1, AssistantMsg));

//            Message UserMsg2 = Message.builder()
//                    .role(Role.USER.getValue())
//                    .content("你是谁？")
//                    .build();
//            ArrayList<Message> userMsgs = new ArrayList<>(Arrays.asList(userMsg1, AssistantMsg, UserMsg2));
//            streamCallWithMessage(gen, userMsgs);
//            userMsgs.add(
//                    Message.builder()
//                    .role(Role.ASSISTANT.getValue())
//                    .content(finalContent.toString())
//                    .build()
//            );
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\n用户输入 > ");
                String userInput = scanner.nextLine();
                userMsgs.add(
                        Message.builder()
                                .role(Role.USER.getValue())
                                .content(userInput)
                                .build()
                );
                streamCallWithMessage(gen, userMsgs);
                userMsgs.add(
                        Message.builder()
                                .role(Role.ASSISTANT.getValue())
                                .content(finalContent.toString())
                                .build()
                );
            }

////             打印最终结果
//            if (reasoningContent.length() > 0) {
//                System.out.println("\n====================完整回复====================");
//                System.out.println(finalContent.toString());
//            }
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            logger.error("An exception occurred: {}", e.getMessage());
        }
        System.exit(0);
    }
}