package com.booklink.dao;

import com.booklink.model.order.OrderCount;
import com.booklink.utils.DBConnectionUtils;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDao {

    public OrderCount findOrderCountAboutBook(Long userId, Long bookId) {
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        String sql = "call order_pkg.find_order_count(?, ?, ?)";
        con = DBConnectionUtils.getConnection();
        try {
            cstmt = con.prepareCall(sql);
            cstmt.setLong(1, bookId);
            cstmt.setLong(2, userId);
            cstmt.registerOutParameter(3, OracleTypes.CURSOR);
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(3);
            Integer count = null;
            if (rs.next()) {
                count = rs.getInt("order_count");
            }
            return new OrderCount(count); // orderCount 의미
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 이 메서드의 목적
    // 주문을 생성한다.
    // 조회?
    public void createOrder(Long bookId, Long userId, int price) {
        // db에 저장할 것
        // 정보를 생성한다.
        Connection con = null;
        CallableStatement cstmt = null;
        String sql = "call order_pkg.create_order(?, ?, ?)";
        con = DBConnectionUtils.getConnection();
        try {
            cstmt = con.prepareCall(sql);
            cstmt.setLong(1, bookId);
            cstmt.setLong(2, userId);
            cstmt.setInt(3, price);
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, null);
        }
    }
}
