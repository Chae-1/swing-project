package com.booklink.ui;

import com.booklink.dao.BookDao;
import com.booklink.model.book.BookDto;
import com.booklink.service.BookService;

import java.time.LocalDate;

public class BookAddForm {
    private BookService bookService;

    public BookAddForm() {
        this.bookService = new BookService(new BookDao());
    }

    public void addBook(BookDto bookDto) {
        bookService.registerBook(bookDto);
    }

    public static void main(String[] args) {
        BookDto bookDto = new BookDto("나의 히어로 아카데미아 40",
                "호리코시 코헤이",
                LocalDate.of(2024, 05, 31),
                "소년만화",
                "내겐 밀리오 같은 용기나, 하도 같은 활약은 할 수 없을지도 몰라. 그렇다 해도 자신을 믿고 동료들과 함께 싸우자! 일어서라! 힘을 잃고도 여전히 인지를 초월한 AFO을 억누르고 있는, 줄곧 동경해온 올마이트의 저 커다란 뒷모습에 지지 않도록!",
                6000,
                "서울미디어코믹스(서울문화사)",
                0, 0.0);
        BookAddForm bookAddForm = new BookAddForm();
        bookAddForm.addBook(bookDto);
    }
}
