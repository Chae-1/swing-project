package com.booklink.dao;

import com.booklink.model.user.User;
import com.booklink.model.user.UserRegistrationDto;
import com.booklink.utils.DBConnectionUtils;
import oracle.jdbc.OracleTypes;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

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
        Connection con = null;
        CallableStatement cstmt = null;
        String sql = "{call user_pkg.ADD_USER(?)}";
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);

            StructDescriptor structDescriptor = StructDescriptor.createDescriptor("USER_FORM", con);
            STRUCT userInfoStruct = new STRUCT(structDescriptor, con, createUserInfo(dto));
            cstmt.setObject(1, userInfoStruct, OracleTypes.STRUCT);
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, null);
        }
    }

    /*
        user_name VARCHAR2(30),
        user_password VARCHAR2(30),
        user_log_id VARCHAR2(30),
        user_registration_date timestamp,
        user_image VARCHAR2(30)
     */
    private Object[] createUserInfo(UserRegistrationDto dto) {
        return new Object[]{
                dto.name(),
                dto.password(),
                dto.loginId(),
                Timestamp.valueOf(dto.registrationDate()),
                dto.image()
        };
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
                    users.add(null);
                }
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void deleteUserById(long userId) {
        String sql = "{call user_pkg.DELETE_USER_BY_ID(?)}"; // 삭제 프로시저 호출

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

    public Optional<User> findById(Long userId) {
        String sql = "{call user_pkg.find_user_by_id(?, ?)}"; // 비밀번호 업데이트를 위한 저장 프로시저 호출
        // open for
        // close
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtils.getConnection();
            cs = con.prepareCall(sql);
            cs.setLong(1, userId);
            cs.registerOutParameter(2, OracleTypes.CURSOR);
            cs.execute();
            rs = (ResultSet) cs.getObject(2);
            User user = null;
            if (rs.next()) {
                user = new User(rs.getLong("user_id"), rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("user_log_id"), rs.getTimestamp("user_registration_date").toLocalDateTime(),
                        rs.getString("user_image"));
            }
            return Optional.ofNullable(user);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("비밀번호 업데이트 중 오류가 발생했습니다. 사용자 ID: " + userId, e);
        } finally {

        }
    }

    public void updatePassword(long userId, String newPassword) {
        String sql = "{call user_pkg.UPDATE_USER_PASSWORD(?, ?)}"; // 비밀번호 업데이트를 위한 저장 프로시저 호출

        try (Connection con = DBConnectionUtils.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setLong(1, userId);
            cs.setString(2, newPassword);
            cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("비밀번호 업데이트 중 오류가 발생했습니다. 사용자 ID: " + userId, e);
        }
    }


}
