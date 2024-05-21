package com.booklink.dao;

import com.booklink.model.book.Book;
import com.booklink.model.book.BookDto;
import com.booklink.utils.DBConnectionUtils;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;

public class BookDao {
    // 등록 수정 삭제 조회


    public void registerBook(BookDto bookDto) {
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtils.getConnection();
            con.prepareCall("");
        } catch ()
    }

    public Optional<Book> findBookByBookName(String title) {
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnectionUtils.getConnection();
            con.prepareCall("");
        }

    }

}
