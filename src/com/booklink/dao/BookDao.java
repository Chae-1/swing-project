package com.booklink.dao;

import com.booklink.model.book.Book;
import com.booklink.model.book.BookDto;
import com.booklink.utils.DBConnectionUtils;
import com.booklink.utils.DbDataTypeMatcher;
import oracle.jdbc.OracleTypes;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
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
        return new Object[]{
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

        String sql = "{ call book_pkg.find_book_by_title(?, ?) }";

        try(Connection con = DBConnectionUtils.getConnection();
            CallableStatement cstmt = con.prepareCall(sql)) {
            cstmt.setString(1, title);
            // Register output parameter
            cstmt.registerOutParameter(2, OracleTypes.STRUCT, "BOOK_REC");
            cstmt.execute();
            STRUCT struct = (STRUCT) cstmt.getObject(2);
            Book findBook = structToBook(struct);
            return Optional.ofNullable(findBook);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Book structToBook(STRUCT struct) throws SQLException {

        Object[] idAndBookInfo = struct.getAttributes();
        if (idAndBookInfo[0] == null) {
            return null;
        }
        Long id = ((BigDecimal) idAndBookInfo[0]).longValue();
        STRUCT bookInfoStruct = (STRUCT) idAndBookInfo[1];
        // 책 정보 구조체에서 책을 정보를 조회한다.
        Object[] bookInfo = bookInfoStruct.getAttributes();
        String title = (String) bookInfo[0];// title
        String author = (String) bookInfo[1];// author
        LocalDate publicationDate = ((Timestamp) bookInfo[2]).toLocalDateTime().toLocalDate();// publicationDate
        int salesPoint = ((BigDecimal) bookInfo[3]).intValue();// salesPoint
        String summary = bookInfo[4].toString();// summary
        String description = bookInfo[5].toString();// description
        int price = ((BigDecimal) bookInfo[6]).intValue();// price
        double rating = ((BigDecimal) bookInfo[7]).doubleValue();
        String publisher = (String) bookInfo[8];// publisher
        Book.BookBuilder bookBuilder = new Book.BookBuilder();

        return bookBuilder.id(id)
                .title(title)
                .author(author)
                .publicationDate(publicationDate)
                .summary(summary)
                .description(description)
                .salesPoint(salesPoint)
                .publisher(publisher)
                .rating(rating)
                .price(price)
                .build();
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
}
