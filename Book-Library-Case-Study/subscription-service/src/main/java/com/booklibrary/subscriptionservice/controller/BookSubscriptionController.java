package com.booklibrary.subscriptionservice.controller;

import com.booklibrary.subscriptionservice.feignclient.BookService;
import com.booklibrary.subscriptionservice.model.Book;
import com.booklibrary.subscriptionservice.model.BookSubscription;
import com.booklibrary.subscriptionservice.service.BookSubscriptionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class BookSubscriptionController {

    @Autowired
    BookSubscriptionService bookSubscriptionService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BookService bookService;

    private static final String BOOK_SERVICE = "bookService";

    @GetMapping("/subscriptions")
    public ResponseEntity<List<BookSubscription>> getBookSubscriptions(@RequestParam(required = false) String subscriberName){
        return new ResponseEntity<>(bookSubscriptionService.getBookSubscriptions(subscriberName), HttpStatus.OK) ;
    }

    @PostMapping("/subscriptions")
    @CircuitBreaker(name = BOOK_SERVICE , fallbackMethod = "fallbackMethodForAddBookSubscription")
    public ResponseEntity<String> addBookSubscription(@RequestBody BookSubscription bookSubscription) {
        if(bookSubscription.getDateReturned() != null){
            return new ResponseEntity<>(bookSubscriptionService.updateBookSubscriptionDateReturned(bookSubscription), HttpStatus.OK) ;
        }
        else{

            HttpHeaders headers = new HttpHeaders();

            //ResponseEntity<Book[]> bookResponse =  restTemplate.exchange("http://BOOK-SERVICE/books", HttpMethod.GET, HttpEntity.EMPTY, Book[].class);
            ResponseEntity<Book[]> bookResponse = bookService.getAllBooks();

            List<Book> listOfBooks = Arrays.asList(bookResponse.getBody());

            Book book = listOfBooks.stream().
                    filter(b -> b.getBookId().equals(bookSubscription.getBookId()) && b.getAvailableCopies() > 0)
                    .findFirst()
                    .orElse(null);

            if(book != null){
                String message = bookSubscriptionService.addBookSubscription(bookSubscription);
                if(message.equals("Successful creation of subscription record")){
                    book.setAvailableCopies(book.getAvailableCopies() - 1);
                    /*HttpEntity <Book> bookEntity = new HttpEntity<>(book, headers);
                    restTemplate.exchange("http://BOOK-SERVICE/books", HttpMethod.POST, bookEntity, Book.class);*/
                    bookService.addBook(book);

                    return new ResponseEntity<>(message,HttpStatus.CREATED);
                }
                else
                    return new ResponseEntity<>(message,HttpStatus.OK);

            }
        }

        return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Book copies not available for subscription");
    }

    public ResponseEntity<String> fallbackMethodForAddBookSubscription(Exception e){
        return new ResponseEntity<>("Book-Service not avaialble",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
