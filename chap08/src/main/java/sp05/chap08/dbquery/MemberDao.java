package sp05.chap08.dbquery;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import sp05.chap08.spring.Member;

import javax.sql.DataSource;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class MemberDao {

    private JdbcTemplate jdbcTemplate;

    public MemberDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Member selectByEmail(String email){
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where EMAIL = ?",
                new MemberRowMapper(),
                email
        );

        return results.isEmpty() ? null : results.get(0);
    }

    public List<Member> selectAll(){
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER",
                new MemberRowMapper()
        );

        return results;
    }

    public int count(){
        Integer count = jdbcTemplate.queryForObject(    // 쿼리 실행 결과 행이 한 개인 경우에만 사용할 수 있다.
                "select count(*) from MEMBER",
                Integer.class);
        return count;
    }

    public void update(Member member){
//        jdbcTemplate.update(
//                "update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
//                member.getName(), member.getPassword(), member.getEmail()
//        );

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        "update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?"
                );


                pstmt.setString(3, member.getName());
                pstmt.setString(2, member.getPassword());
                pstmt.setString(1, member.getEmail());
//                pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));

                return pstmt;
            }
        });
    }

    public void insert(final Member member){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into MEMBER(EMAIL, PASSWORD, NAME, REGDATE) values(?, ?, ?, ?)",
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.TIMESTAMP
        );
//        pscf.setReturnGeneratedKeys(true);
        pscf.setGeneratedKeysColumnNames("ID");    //블로그에 정리해두었음 https://velog.io/@hamkua/Spring-JDBC-Generated-keys-not-requested
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(member.getEmail(), member.getPassword(), member.getName(), member.getRegisterDateTime()));


//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement pstmt = con.prepareStatement(
//                        "insert into MEMBER(EMAIL, PASSWORD, NAME, REGDATE)" +
//                                "values(?, ?, ?, ?)",
//                        new String[] {"ID"}
//                );
//
//                pstmt.setString(1, member.getEmail());
//                pstmt.setString(2, member.getPassword());
//                pstmt.setString(3, member.getName());
//                pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
//                return pstmt;
//            }
//        }, keyHolder);
        jdbcTemplate.update(psc, keyHolder);

        Number keyValue = keyHolder.getKey();
        member.setId(keyValue.longValue());
    }

    class MemberRowMapper implements RowMapper<Member>{

        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member(
                    rs.getString("EMAIL"),
                    rs.getString("PASSWORD"),
                    rs.getString("NAME"),
                    rs.getTimestamp("REGDATE").toLocalDateTime());

            member.setId(rs.getLong("ID"));
            return member;
        }
    }
}
