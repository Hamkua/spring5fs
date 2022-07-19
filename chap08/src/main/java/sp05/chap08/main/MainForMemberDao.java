package sp05.chap08.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sp05.chap08.config.AppCtx;
import sp05.chap08.dbquery.MemberDao;
import sp05.chap08.spring.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainForMemberDao {
    private static MemberDao memberDao;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppCtx.class);
        memberDao = ac.getBean(MemberDao.class);

        selectAll();
        updateMember();
        insertMember();

        ac.close();
    }

    private static void selectAll(){
        System.out.println("-----selectAll");
        int total = memberDao.count();
        System.out.println("전체 데이터: " + total);
        List<Member> members = memberDao.selectAll();
        for(Member m : members){
            System.out.println(m.getId() + ":" + m.getEmail() + ":" + m.getName());
        }
    }

    private static void updateMember(){
        System.out.println("-----updateMember");
        Member member = memberDao.selectByEmail("madvirus@madvirus.net");
        String oldPw = member.getPassword();
        String newPw = Double.toHexString(Math.random());
        member.changePassword(oldPw, newPw);

        memberDao.update(member);
        System.out.println("암호 변경: " + oldPw + " > " + newPw);
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");

    private static void insertMember(){
        System.out.println("-----insertMember");

        String prefix = formatter.format(LocalDateTime.now());
        Member member = new Member(prefix +"@test.com",
                prefix, prefix, LocalDateTime.now());
        memberDao.insert(member);
        System.out.println(member.getId() + " 데이터 추가");
    }
}
