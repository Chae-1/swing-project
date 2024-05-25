package com.booklink.dao;

import com.booklink.model.book.comments.CommentDto;
import com.booklink.model.book.comments.CommentFormDto;
import com.booklink.utils.DBConnectionUtils;
import com.booklink.utils.DbDataTypeMatcher;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Result;
import oracle.jdbc.OracleTypes;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class CommentDao {
    public void registerComment(Long userId, Long bookId, int rating, String text) {
        Connection con = null;
        CallableStatement cstmt = null;
        String sql = "call register_comment(?, ?, ?, ?)";
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setLong(1, userId);
            cstmt.setLong(2, bookId);
            cstmt.setInt(3, rating);
            cstmt.setString(4, text);
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, null);
        }
    }

    public void registerComment(CommentFormDto dto, String purchaseStatus) {
        Connection con = null;
        CallableStatement cstmt = null;
        String sql = "call comment_pkg.register_comment(?)";
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            StructDescriptor commentDescriptor = new StructDescriptor("COMMENT_REG", con);
            STRUCT commentStruct = new STRUCT(commentDescriptor, con, commentInfo(dto, purchaseStatus, con));
            cstmt.setObject(1, commentStruct, OracleTypes.STRUCT);
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, null);
        }
    }

    public List<CommentDto> findAllCommentById(Long bookId) {
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        String sql = "call comment_pkg.find_book_comments(?, ?)";
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setLong(1, bookId);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(2);
            List<CommentDto> commentDtos = new ArrayList<>();
            while (rs.next()) {
                commentDtos.add(new CommentDto(
                        rs.getLong("comment_id"),
                        rs.getString("user_name"),
                        rs.getInt("comment_rating"),
                        rs.getTimestamp("comment_reg_date").toLocalDateTime(),
                        rs.getString("comment_content")
                ));
            }
            return commentDtos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, null);
        }
    }

    private Object[] commentInfo(CommentFormDto dto, String purchaseStatus, Connection con) {
        return new Object[]{
                dto.userId(),
                dto.bookId(),
                dto.rating(),
                Timestamp.valueOf(dto.regDate()),
                DbDataTypeMatcher.stringToClob(con, dto.content()),
                purchaseStatus
        };
    }

}
