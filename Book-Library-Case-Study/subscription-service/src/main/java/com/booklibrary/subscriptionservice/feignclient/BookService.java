package com.booklibrary.subscriptionservice.feignclient;

import com.booklibrary.subscriptionservice.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "BOOK-SERVICE")
public interface BookService {

    @GetMapping("/books")
    ResponseEntity<Book[]> getAllBooks();

    @PostMapping("/books")
    ResponseEntity<Book> addBook(@RequestBody Book book);
}
