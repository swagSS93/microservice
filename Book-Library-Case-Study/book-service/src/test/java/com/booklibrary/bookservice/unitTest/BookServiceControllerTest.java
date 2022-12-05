package com.booklibrary.bookservice.unitTest;

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
    private BookServiceController mockBookServiceControllerTest;

    @Mock
    private BookService mockBookService;

    @Test
     void getAllBooksTest(){
       when(mockBookService.getAllBooks()).thenReturn(new ArrayList<>());
       ResponseEntity<List<Book>> response = mockBookServiceControllerTest.getAllBooks();
       assertEquals(200,response.getStatusCodeValue());
   }

    @Test
    void addBookTest(){
        Book book = new Book();
        when(mockBookService.addBook(book)).thenReturn(book);
        ResponseEntity<Book> response = mockBookServiceControllerTest.addBook(book);
        assertEquals(201,response.getStatusCodeValue());
    }
}


