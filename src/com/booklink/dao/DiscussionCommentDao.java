package com.booklink.dao;

import com.booklink.model.book.disscussion.comment.DiscussionCommentDto;
import com.booklink.model.book.disscussion.comment.DiscussionCommentRegisterDto;
import com.booklink.utils.DBConnectionUtils;
import com.booklink.utils.DBDataTypeMatcher;
import oracle.jdbc.OracleTypes;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscussionCommentDao {
    public void registerDiscComment(DiscussionCommentRegisterDto discussionCommentDto) {
        Connection con = null;
        CallableStatement cstmt = null;
        String sql = "call disc_com_pkg.register_disc_comment(?)";

        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            StructDescriptor structDescriptor = new StructDescriptor("disc_com_reg".toUpperCase(), con);
            STRUCT struct = new STRUCT(structDescriptor, con, toObj(discussionCommentDto, con));
            cstmt.setObject(1, struct, OracleTypes.STRUCT);

            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, null);
        }
    }

    private Object[] toObj(DiscussionCommentRegisterDto discussionCommentDto, Connection con) {
        return new Object[] {
                discussionCommentDto.userId(),
                discussionCommentDto.discussionId(),
                Timestamp.valueOf(discussionCommentDto.now()),
                DBDataTypeMatcher.stringToClob(con, discussionCommentDto.content())
        };
    }

    public List<DiscussionCommentDto> findAllDiscCommentByDiscId(Long discId) {
        List<DiscussionCommentDto> list = new ArrayList<>();
        String sql = "call disc_com_pkg.find_disc_comment(?, ?)";
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setLong(1, discId);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            cstmt.execute();

            rs = (ResultSet) cstmt.getObject(2);
            while (rs.next()) {
                list.add(new DiscussionCommentDto(rs.getLong("dis_comment_id"),
                        rs.getString("user_name"),
                        rs.getString("dis_comment_content"),
                        rs.getTimestamp("dis_comment_registration_date").toLocalDateTime(),
                        rs.getLong("user_id")));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, rs);
        }
    }

    public Optional<DiscussionCommentDto> findByCommentId(Long commentId) {
        String sql = "call disc_com_pkg.find_comment_by_id(?, ?)";
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setLong(1, commentId);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            cstmt.execute();

            rs = (ResultSet) cstmt.getObject(2);
            DiscussionCommentDto dto = null;
            if (rs.next()) {
                dto = new DiscussionCommentDto(rs.getLong("dis_comment_id"),
                        rs.getString("user_name"),
                        rs.getString("dis_comment_content"),
                        rs.getTimestamp("dis_comment_registration_date").toLocalDateTime(),
                        rs.getLong("user_id"));
            }
            return Optional.ofNullable(dto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, rs);
        }
    }

    public void removeCommentById(Long commentId) {
        Connection con = null;
        CallableStatement cstmt = null;
        String sql = "call disc_com_pkg.remove_by_id(?)";

        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setLong(1, commentId);
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, null);
        }
    }
}
