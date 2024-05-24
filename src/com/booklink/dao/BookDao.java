package com.booklink.dao;

import com.booklink.model.book.Book;
import com.booklink.model.book.BookDto;
import com.booklink.utils.DBConnectionUtils;
import com.booklink.utils.DbDataTypeMatcher;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao {

    public void registerBooks(List<BookDto> bookDtos) {
        Connection con = null;
        CallableStatement cstmt = null;
        String sql = "call book_pkg.add_books(?)";
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);

            StructDescriptor structDescriptor = StructDescriptor.createDescriptor("BOOK_INFO_REC", con);
            ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor("BOOK_INFO_TAB", con);
            STRUCT[] structArray = new STRUCT[bookDtos.size()];
            for (int i = 0; i < bookDtos.size(); i++) {
                structArray[i] = new STRUCT(structDescriptor, con, createBookInfo(con, bookDtos.get(i)));
            }
            cstmt.setArray(1, new ARRAY(arrayDescriptor, (OracleConnection) con, structArray));
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, null);
        }
    }

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
        System.out.println(bookDto.rating());
        return new Object[]{
                bookDto.title(),
                bookDto.author(),
                Date.valueOf(bookDto.publicationDate()),
                bookDto.salesPoint(),
                DbDataTypeMatcher.stringToClob(con, bookDto.summary()),
                DbDataTypeMatcher.stringToClob(con, bookDto.description()),
                bookDto.price(),
                bookDto.rating() != null ? new BigDecimal(bookDto.rating()) : null,
                bookDto.publisher()
        };
    }

    public Optional<Book> findBookByTitle(String title) {

        String sql = "{ call book_pkg.find_book_by_title(?, ?) }";
        ResultSet rs = null;

        try(Connection con = DBConnectionUtils.getConnection();
            CallableStatement cstmt = con.prepareCall(sql)) {
            cstmt.setString(1, title);
            // Register output parameter
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(2);
            Book book = null;
            if (rs.next()) {
                book = getBook(rs);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(Long bookId) {
           String sql = "{call book_pkg.delete_book(?)}";
        try(Connection con = DBConnectionUtils.getConnection();
            CallableStatement cstmt = con.prepareCall(sql)) {
            cstmt.setLong(1, bookId);
            // Register output parameter
            int result = cstmt.executeUpdate();
            if (result > 0) {
                System.out.println("성공적으로 삭제했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBook(Long bookId, BookDto bookDto) {
        String sql = "{call book_pkg.update_book(?, ?)}";

        try(Connection con = DBConnectionUtils.getConnection();
            CallableStatement cstmt = con.prepareCall(sql)) {
            cstmt.setLong(1, bookId);
            StructDescriptor structDescriptor = StructDescriptor.createDescriptor("BOOK_INFO_REC", con);
            STRUCT bookInfoStruct = new STRUCT(structDescriptor, con, createBookInfo(con, bookDto));
            cstmt.setObject(2, bookInfoStruct, OracleTypes.STRUCT);
            int result = cstmt.executeUpdate();
            if (result > 0) {
                System.out.println("성공적으로 업데이트 되었습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Book> findBookById(Long bookId) {
        String sql = "{call book_pkg.find_book_by_id(?, ?)}";
        ResultSet rs = null;
        try(Connection con = DBConnectionUtils.getConnection();
            CallableStatement cstmt = con.prepareCall(sql)) {
            cstmt.setLong(1, bookId);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            // Register output parameter
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(2);
            Book book = null;
            if (rs.next()) {
                book = getBook(rs);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Book getBook(ResultSet rs) throws SQLException {
        double bookRating = rs.getBigDecimal("book_rating").doubleValue();
        System.out.println("bookRating: " + bookRating);
        return new Book.BookBuilder()
                .id(rs.getLong("book_id"))
                .title(rs.getString("book_title"))
                .author(rs.getString("book_author"))
                .publicationDate(rs.getDate("book_publication_date").toLocalDate())
                .summary(rs.getString("book_summary"))
                .description(rs.getString("book_description"))
                .price(rs.getInt("book_price"))
                .publisher(rs.getString("book_publisher"))
                .salesPoint(rs.getInt("book_sales_point"))
                .rating(bookRating)
                .build();
    }

    public List<Book> findAllBook() {
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        String sql = "call book_pkg.find_all_book(?)";
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(1);
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(getBook(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, rs);
        }
    }

    public List<Book> findAllBookWithCount() {
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        String sql = "call book_pkg.find_all_book_with_count(?)";
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(1);
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(getBook(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, rs);
        }
    }

    public List<Book> findBookContainsTitle(String title) {
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        String sql = "call book_pkg.find_book_contains_title(?, ?)";
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setString(1, title);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(2);
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(getBook(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, rs);
        }
    }

    public List<Book> findBookByCategoryName(String categoryName) {
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        String sql = "call book_pkg.find_books_by_cat_name(?, ?)";
        try {
            con = DBConnectionUtils.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setString(1, categoryName);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(2);
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(getBook(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionUtils.releaseConnection(con, cstmt, rs);
        }
    }
}
