package com.booklink.dao;

import com.booklink.model.user.User;
import com.booklink.model.user.UserRegistrationDto;
import com.booklink.utils.DBConnectionUtils;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * create table users (
 *                        user_id int,
 *                        user_name VARCHAR2(30),
 *                        user_password VARCHAR2(30),
 *                        user_log_id VARCHAR2(30),
 *                        user_registration_date timestamp,
 *                        user_image VARCHAR2(30)
 * );
 *
 * create or replace package user_pkg as
 *     procedure add_user(p_user_form in user_form);
 *     procedure find_user_by_logid(
 *         p_user_log_idin users.user_log_id%type,
 *         p_user_password in users.user_password%type
 *     );
 *     --procedure find_all_user();
 *     procedure delete_user_by_id(p_user_id in users.user_id%type);
 *     -- user 변경 및 선호 카테고리를 변경할 수 있도록 설정
 * end user_pkg;
 * /
 */
public class UserDao {

    public void addUser(UserRegistrationDto dto) {
        // dto에 있는 데이터를 꺼내서 DB 에 저장
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        String sql = "call user_pkg.add_user(?, ?, ?)";
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setString(1, "");
//            cstmt.setString(2, logId);
            cstmt.registerOutParameter(3, OracleTypes.CURSOR);
            cstmt.execute();

            rs = (ResultSet) cstmt.getObject(3);
            User user = null;
            if (rs.next()) {
                user = getUser(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, rs);
        }
    }


    public Optional<User> findByLogIdAndPassword(String logId, String password) {
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        String sql = "call user_pkg.find_user_by_logid(?, ?, ?)";
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setString(1, logId);
            cstmt.setString(2, password);
            cstmt.registerOutParameter(3, OracleTypes.CURSOR);
            cstmt.execute();

            rs = (ResultSet) cstmt.getObject(3);
            User user = null;
            if (rs.next()) {
                user = getUser(rs);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, rs);
        }
    }

    private static User getUser(ResultSet rs) throws SQLException {
        return new User(rs.getLong("user_id"),
                rs.getString("user_name"),
                rs.getString("user_password"),
                rs.getString("user_log_id"),
                rs.getTimestamp("user_registration_date").toLocalDateTime(),
                rs.getString("user_image"));
    }

    public List<User> findAll() {
        // database에 접근해서 모든 user 정보를 리스트로 반환
        return null;
    }
}
