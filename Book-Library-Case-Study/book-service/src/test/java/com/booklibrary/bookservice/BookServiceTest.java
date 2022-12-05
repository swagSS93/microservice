package com.booklibrary.bookservice;
import com.booklibrary.bookservice.model.Book;
import com.booklibrary.bookservice.repository.BookRepository;
import com.booklibrary.bookservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private Book book;

    @BeforeEach
    void setUp(){
        book.setBookId("B1212");
        book.setId(1L);
    }

    @Test
    void getAllBooksTest(){
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(bookRepository.findAll()).thenReturn(bookList);
        
        List<Book> response = bookService.getAllBooks();
        assertEquals(1,response.size());
    }

    @Test
    void addBookTest(){
        when(book.getId()).thenReturn(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book response = bookService.addBook(book);
        assertEquals(1L,response.getId());
    }
    
}
