package com.booklibrary.bookservice;

import com.booklibrary.bookservice.controller.BookServiceController;
import com.booklibrary.bookservice.model.Book;
import com.booklibrary.bookservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceControllerTest {

    @InjectMocks
    private BookServiceController bookServiceController;

    @Mock
    private BookService bookService;

    @Test
     void getAllBooksTest(){
       when(bookService.getAllBooks()).thenReturn(new ArrayList<>());
       ResponseEntity<List<Book>> response = bookServiceController.getAllBooks();
       assertEquals(200,response.getStatusCodeValue());
   }

    @Test
    void addBookTest(){
        Book book = new Book();
        when(bookService.addBook(book)).thenReturn(book);
        ResponseEntity<Book> response = bookServiceController.addBook(book);
        assertEquals(201,response.getStatusCodeValue());
    }
}


