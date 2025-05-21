package com.kevinqiu.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

@Slf4j
public class DynamicProxyMain {

    // 通过 JDK 的方式实现动态代理，进而实现 AOP
    // 实现原理：
    /*
    在实际运行过程中，JDK 在执行Proxy.newProxyInstance时会动态生成一个 $Proxy01 类，
    这个类继承了 Proxy 类、实现了用户传入的接口，通过用户传入的反射处理器对象实现用户传入的接口，
    实现接口的方式是通过用户传入的 InvocationHandler 中的 invoke 方法，
    具体来说就是在接口的重写方法中，通过分析方法签名，调用 InvocationHandler 中的 invoke 方法，完成接口实现
    举例来说，在调用 controllerProxy.printResult() 时，实际调用的是 $Proxy01 对象的printResult方法，而这个方法的实质就是
    调用 JDKInvocationHandler 的 invoke 方法，最终实现了方法的交付调用，实现了 AOP 设计模式。
     */
    public static void JDKDynamicProxy() {
        ObjectController objectController = new DemoController();
        // 创建动态代理对象
        ObjectController controllerProxy = (ObjectController) Proxy.newProxyInstance(
                objectController.getClass().getClassLoader(),
                new Class[]{ObjectController.class},
                new JDKInvocationHandler(objectController)
        );
        // 通过代理调用方法
        String s = controllerProxy.printResult();
        log.info("JDKDynamicProxy: {}", s);
    }

    // 通过 CGLib 的方式实现动态代理，进而实现 AOP
    // CGLib 与 JDK 动态代理的不同点在于，JDK 只能实现接口的代理，而 CGLib 既可以进行接口的代理也可以进行类的代理
    public static void CGLibDynamicProxy(){
        DemoController demoController = new DemoController();
        // 创建动态代理对象
        DemoController controllerProxy = (DemoController) Enhancer.create(
                demoController.getClass(), new CGLibInterceptor(demoController));
        // 通过代理调用方法
        String s = controllerProxy.printResult();
        log.info("CGLibDynamicProxy: {}", s);
    }

    public static void main(String[] args) {
//        JDKDynamicProxy();
        CGLibDynamicProxy();
    }
}
