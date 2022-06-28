package main;

import chap07.Calculator;
import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspect {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
        Calculator cal = ctx.getBean("calculator", Calculator.class);
        /*
        calculator.class 대신 RecCalculator.class 타입의 빈을 찾도록 수정하면,
        calculator인터페이스를 상속하고 있으므로, 그 인터페이스를 이용해 프록시를 생성하게 되어 문제가 발생한다.
         */


        long fiveFact = cal.factorial(5);
        System.out.println("cal.factorial(5) = " + fiveFact);
        System.out.println(cal.getClass().getName());
        ctx.close();

        /*
        RecCalculator.factorial([5]) 실행 시간 : 24208 ns
        cal.factorial(5) = 120
        com.sun.proxy.$Proxy18   <- 프록시 객체!
         */
    }
}
