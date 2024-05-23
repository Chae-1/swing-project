package com.booklink.dao;

import com.booklink.model.user.User;
import com.booklink.model.user.UserRegistrationDto;
import com.booklink.utils.DBConnectionUtils;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * create table users (
 * user_id int,
 * user_name VARCHAR2(30),
 * user_password VARCHAR2(30),
 * user_log_id VARCHAR2(30),
 * user_registration_date timestamp,
 * user_image VARCHAR2(30)
 * );
 * <p>
 * create or replace package user_pkg as
 * procedure add_user(p_user_form in user_form);
 * procedure find_user_by_logid(
 * p_user_log_idin users.user_log_id%type,
 * p_user_password in users.user_password%type
 * );
 * --procedure find_all_user();
 * procedure delete_user_by_id(p_user_id in users.user_id%type);
 * -- user 변경 및 선호 카테고리를 변경할 수 있도록 설정
 * end user_pkg;
 * /
 */
public class UserDao {

    public void registerUser(UserRegistrationDto dto) {
        String sql = "{call ADD_USER(?, ?, ?, ?, ?)}"; //저장 프로지서 호출

        try (Connection con = DBConnectionUtils.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            // dto에 있는 데이터를 꺼내서 DB 에 저장
            cs.setString(1, dto.name());
            cs.setString(2, dto.password());
            cs.setString(3, dto.loginId());
            cs.setTimestamp(4, Timestamp.valueOf(dto.registrationDate()));
            cs.setString(5, dto.image());

            cs.executeUpdate();

            System.out.println("등록완료");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "{call GET_ALL_USERS(?)}"; // 조회 프로시저 호출
        try (Connection con = DBConnectionUtils.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    user.setLoginId(rs.getString("loginId"));
                    Timestamp registrationDate = rs.getTimestamp("registrationDate");
                    user.setRegistrationDate(registrationDate != null ? registrationDate.toLocalDateTime() : null);
                    user.setImage(rs.getString("image"));
                    users.add(user);
                }
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

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


    public void deleteUserById(long userId) {
        String sql = "{call DELETE_USER_BY_ID(?)}"; // 삭제 프로시저 호출

        try (Connection con = DBConnectionUtils.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setLong(1, userId);
            cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while deleting user with ID: " + userId, e);
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

}
