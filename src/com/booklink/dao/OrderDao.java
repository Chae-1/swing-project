package com.booklink.dao;

import com.booklink.model.order.OrderCount;
import com.booklink.model.order.OrderDto;
import com.booklink.utils.DBConnectionUtils;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<OrderDto> findAllOrderByUserId(Long userId) {
        List<OrderDto> orders = new ArrayList<>();
        Connection con = null;
        CallableStatement cstmt = null;
        String sql = "{call order_pkg.find_orders_by_user_id(?, ?)}"; // 프로시저 호출문

        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setLong(1, userId);
            cstmt.registerOutParameter(2, OracleTypes.ARRAY, "ORDER_TABLE");
            cstmt.execute();

            ARRAY orderArray = (ARRAY) cstmt.getObject(2);
            ResultSet rs = orderArray.getResultSet();

            while (rs.next()) {
                STRUCT orderStruct = (STRUCT) rs.getObject(2);
                Object[] attrs = orderStruct.getAttributes();

                OrderDto order = new OrderDto(
                        ((Number) attrs[0]).longValue(),
                        ((Number) attrs[1]).longValue(),
                        (String) attrs[2],
                        ((Timestamp) attrs[3]).toLocalDateTime(),
                        ((Number) attrs[4]).intValue(),
                        (String) attrs[5]
                );

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, null);
        }

        return orders;
    }
}
