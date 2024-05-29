package com.booklink.dao;

import com.booklink.model.book.disscussion.BookDiscussion;
import com.booklink.model.book.disscussion.BookDiscussionDto;
import com.booklink.model.book.disscussion.BookDiscussionRegisterForm;
import com.booklink.utils.DBConnectionUtils;
import com.booklink.utils.DBDataTypeMatcher;
import oracle.jdbc.OracleTypes;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDiscussionDao {

    public void registerDiscussion(BookDiscussionRegisterForm form) {
        String sql = "call book_disc_pkg.add_book_disc(?)";
        Connection con = null;
        CallableStatement cstmt = null;

        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            StructDescriptor structDescriptor = new StructDescriptor("book_disc_obj".toUpperCase(), con);
            STRUCT struct = new STRUCT(structDescriptor, con, createObj(form, con));
            cstmt.setObject(1, struct, OracleTypes.STRUCT);
            cstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, null);
        }
    }

    private Object[] createObj(BookDiscussionRegisterForm form, Connection con) {
        return new Object[]{
                form.bookId(),
                form.userId(),
                form.title().toString(),
                DBDataTypeMatcher.stringToClob(con, form.content()),
        };
    }

    public List<BookDiscussionDto> findAllDiscussionAboundBook(Long bookId) {
        String sql = "call book_disc_pkg.find_all(?, ?)";
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setLong(1, bookId);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(2);
            List<BookDiscussionDto> bookDiscussions = new ArrayList<>();
            while (rs.next()) {
                bookDiscussions.add(getBookDisc(rs));
            }
            return bookDiscussions;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, rs);
        }
    }

    private BookDiscussionDto getBookDisc(ResultSet rs) throws SQLException {
        return new BookDiscussionDto(
                rs.getLong("discussion_id"),
                rs.getTimestamp("discussion_date").toLocalDateTime(),
                rs.getString("discussion_content"),
                rs.getString("discussion_title"),
                rs.getLong("book_id"),
                rs.getLong("user_id"),
                rs.getString("user_name")
        );
    }
}
