package com.booklibrary.bookservice.unitTest;
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
    private BookService mockBookServiceTest;

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private Book mockBook;

    @BeforeEach
    void setUp(){
        mockBook.setBookId("B1212");
        mockBook.setId(1L);
    }

    @Test
    void getAllBooksTest(){
        List<Book> bookList = new ArrayList<>();
        bookList.add(mockBook);
        when(mockBookRepository.findAll()).thenReturn(bookList);
        
        List<Book> response = mockBookServiceTest.getAllBooks();
        assertEquals(1,response.size());
    }

    @Test
    void addBookTest(){
        when(mockBook.getId()).thenReturn(1L);
        when(mockBookRepository.findById(1L)).thenReturn(Optional.of(mockBook));

        Book response = mockBookServiceTest.addBook(mockBook);
        assertEquals(1L,response.getId());
    }
    
}
