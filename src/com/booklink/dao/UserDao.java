package com.booklink.dao;

import com.booklink.model.user.User;
import com.booklink.model.UserRegistrationDto;
import com.booklink.utils.DBConnectionUtils;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public void registerUser(UserRegistrationDto dto) {
        String sql = "{call ADD_USER(?, ?, ?, ?, ?)}"; //저장 프로지서 호출

        try (Connection con = DBConnectionUtils.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            // dto에 있는 데이터를 꺼내서 DB 에 저장
            cs.setString(1, dto.getName());
            cs.setString(2, dto.getPassword());
            cs.setString(3, dto.getLoginId());
            cs.setTimestamp(4, Timestamp.valueOf(dto.getRegistrationDate()));
            cs.setString(5, dto.getImage());

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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
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
}


