package sp05.chap08.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sp05.chap08.dbquery.MemberDao;
import sp05.chap08.spring.*;


@Configuration
@EnableTransactionManagement
public class AppCtx {

    @Bean(destroyMethod = "close")
    public DataSource dataSource(){
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8");
        ds.setUsername("spring5");
        ds.setPassword("spring5");
        ds.setInitialSize(2);    //커넥션 풀을 초기화할 때 생성될 초기 커넥션 개수, 기본값 10
        ds.setMaxActive(100);    //커넥션 풀에서 가져올  수 있는 최대 커넥션 개수, 기본값 100
        ds.setTestWhileIdle(true);    //유휴 커넥션 검사
        ds.setMinEvictableIdleTimeMillis(1000 * 60 * 3);    // 최소 유휴시간 3분
        ds.setTimeBetweenEvictionRunsMillis(1000 * 10);    //10초 주기로 검사
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        /*
        PlatformTransactionManager는 스프링이 제공하는 트랜잭션 매니저 인터페이스임.
        DataSourceTransactionManager를 PlatformTransactionManager로 사용함
         */
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }

    @Bean
    public MemberDao memberDao(){
        return new MemberDao(dataSource());
    }

    @Bean
    public ChangePasswordService changePwdSvc(){
        ChangePasswordService pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao());
        return pwdSvc;
    }

    @Bean
    public MemberRegisterService memberRegSvc(){
        return new MemberRegisterService(memberDao());
    }

    @Bean
    public MemberPrinter memberPrinter(){
        return new MemberPrinter();
    }

    @Bean
    public MemberListPrinter listPrinter(){
        return new MemberListPrinter(memberDao(), memberPrinter());
    }

    @Bean
    public MemberInfoPrinter infoPrinter(){
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        infoPrinter.setMemberDao(memberDao());
        infoPrinter.setPrinter(memberPrinter());
        return infoPrinter;
    }

}
