package chap09.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*

주석처리. SprigApplication.run()으로 실행하면 빈 등록에 중복이 발생한다 !

@Configuration
public class ControllerConfig {

    @Bean
    public HelloController helloController(){
        return new HelloController();
    }

}

*/
