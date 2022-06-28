package main;

import chap07.Calculator;
import config.AppCtxWithCache;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspectWithCache {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtxWithCache.class);

        Calculator cal = ctx.getBean("calculator", Calculator.class);
        cal.factorial(7);
        cal.factorial(7);
        cal.factorial(5);
        cal.factorial(5);
        ctx.close();

        /*
        RecCalculator.factorial([7]) 실행 시간 : 18459 ns
        CacheAspect: Cache에 추가[7]
        CacheAspect: Cache에서 구함[7]
        RecCalculator.factorial([5]) 실행 시간 : 4542 ns
        CacheAspect: Cache에 추가[5]
        CacheAspect: Cache에서 구함[5]

        어떤 Aspect가 먼저 적용될지 정하려면 @Order 사용하자!
         */
    }
}
