package com.booklink.dao;

import com.booklink.model.book.Book;
import com.booklink.model.book.BookDto;
import com.booklink.utils.DBConnectionUtils;
import com.booklink.utils.DbDataTypeMatcher;
import oracle.jdbc.OracleTypes;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import java.sql.*;
import java.util.Optional;

public class BookDao {
    // 등록 수정 삭제 조회


    public void registerBook(BookDto bookDto) {
        Connection con = null;
        CallableStatement cstmt = null;
        String sql = "{call book_pkg.add_book(?)}";

        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            StructDescriptor structDescriptor = StructDescriptor.createDescriptor("BOOK_INFO_REC", con);
            STRUCT bookInfoStruct = new STRUCT(structDescriptor, con, createBookInfo(con, bookDto));

            cstmt.setObject(1, bookInfoStruct, OracleTypes.STRUCT);
            cstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Object[] createBookInfo(Connection con, BookDto bookDto) {
        return new Object[] {
                bookDto.title(),
                bookDto.author(),
                Date.valueOf(bookDto.publicationDate()),
                bookDto.salesPoint(),
                DbDataTypeMatcher.stringToClob(con, bookDto.summary()),
                DbDataTypeMatcher.stringToClob(con, bookDto.description()),
                bookDto.price(),
                bookDto.rating(),
                bookDto.publisher()
        };
    }

    public Optional<Book> findBookByTitle(String title) {
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        return Optional.ofNullable(null);

    }

}
