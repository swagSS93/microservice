package com.booklibrary.bookservice.service;

import com.booklibrary.bookservice.model.Book;
import com.booklibrary.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book addBook(Book book){
        bookRepository.save(book);
        return bookRepository.findById(book.getId()).get();
    }
}
