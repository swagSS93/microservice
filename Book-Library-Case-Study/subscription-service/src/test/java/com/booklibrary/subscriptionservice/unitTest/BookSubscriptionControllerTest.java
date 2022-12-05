package com.booklibrary.subscriptionservice.unitTest;

import com.booklibrary.subscriptionservice.controller.BookSubscriptionController;
import com.booklibrary.subscriptionservice.feignclient.BookService;
import com.booklibrary.subscriptionservice.model.Book;
import com.booklibrary.subscriptionservice.model.BookSubscription;
import com.booklibrary.subscriptionservice.service.BookSubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookSubscriptionControllerTest {

    @InjectMocks
    BookSubscriptionController mockBookSubscriptionControllerTest;

    @Mock
    BookSubscriptionService mockBookSubscriptionService;

    @Mock
    private BookSubscription mockBookSubscription;

    @Mock
    private BookService mockBookService;

    @Mock
    private ResponseEntity<Book[]> mockBooks;

    private Book book;

    @BeforeEach
    void setUp(){
        mockBookSubscription.setBookId("B1212");
        mockBookSubscription.setId(1L);
        book = new Book();
        book.setBookId("B1212");
    }

    @Test
    void getBookSubscriptionsTest(){
        List<BookSubscription> bookSubscriptionList = new ArrayList<>();
        bookSubscriptionList.add(mockBookSubscription);
        when(mockBookSubscriptionService.getBookSubscriptions(anyString())).thenReturn(bookSubscriptionList);

        ResponseEntity<List<BookSubscription>> response = mockBookSubscriptionControllerTest.getBookSubscriptions(anyString());

        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    void addBookSubscriptionDateReturnedNotNullTest(){
        when(mockBookSubscription.getDateReturned()).thenReturn(LocalDate.now());
        when(mockBookSubscriptionService.updateBookSubscriptionDateReturned(mockBookSubscription)).thenReturn("UPDATED");

        ResponseEntity<String> response = mockBookSubscriptionControllerTest.addBookSubscription(mockBookSubscription);

        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    void addBookSubscriptionDateReturnedNullAndBookSubscriptionSubscriptionCopiesPresentTest(){
        book.setAvailableCopies(2);
        Book[] arrayOfBooks = new Book[1];
        arrayOfBooks[0] = book;

        when(mockBookSubscription.getDateReturned()).thenReturn(null);
        when(mockBookService.getAllBooks()).thenReturn(mockBooks);
        when(mockBooks.getBody()).thenReturn(arrayOfBooks);
        when(mockBookSubscription.getBookId()).thenReturn("B1212");
        when(mockBookSubscriptionService.addBookSubscription(mockBookSubscription)).thenReturn("Successful creation of subscription record");
        when(mockBookService.addBook(book)).thenReturn(new ResponseEntity<>(book, HttpStatus.CREATED));

        ResponseEntity<String> response = mockBookSubscriptionControllerTest.addBookSubscription(mockBookSubscription);

        assertEquals(201,response.getStatusCodeValue());
        assertEquals(1,book.getAvailableCopies());
    }

    @Test
    void addBookSubscriptionDateReturnedNullAndBookSubscriptionCopiesPresentButAlreadyAddedTest(){
        book.setAvailableCopies(2);
        Book[] arrayOfBooks = new Book[1];
        arrayOfBooks[0] = book;

        when(mockBookSubscription.getDateReturned()).thenReturn(null);
        when(mockBookService.getAllBooks()).thenReturn(mockBooks);
        when(mockBooks.getBody()).thenReturn(arrayOfBooks);
        when(mockBookSubscription.getBookId()).thenReturn("B1212");
        when(mockBookSubscriptionService.addBookSubscription(mockBookSubscription)).thenReturn("Already added subscription record");

        ResponseEntity<String> response = mockBookSubscriptionControllerTest.addBookSubscription(mockBookSubscription);

        assertEquals(200,response.getStatusCodeValue());
        assertEquals(2,book.getAvailableCopies());
        verify(mockBookService, never()).addBook(book);
    }

    @Test
    void addBookSubscriptionDateReturnedNullAndBookSubscriptionCopiesNotPresentTest(){
        book.setAvailableCopies(0);
        Book[] arrayOfBooks = new Book[1];
        arrayOfBooks[0] = book;

        when(mockBookSubscription.getDateReturned()).thenReturn(null);
        when(mockBookService.getAllBooks()).thenReturn(mockBooks);
        when(mockBooks.getBody()).thenReturn(arrayOfBooks);
        when(mockBookSubscription.getBookId()).thenReturn("B1212");

        ResponseEntity<String> response = mockBookSubscriptionControllerTest.addBookSubscription(mockBookSubscription);

        assertEquals(422,response.getStatusCodeValue());
        assertEquals(0,book.getAvailableCopies());
        verify(mockBookService, never()).addBook(book);
        verify(mockBookSubscriptionService, never()).addBookSubscription(mockBookSubscription);
    }
}
