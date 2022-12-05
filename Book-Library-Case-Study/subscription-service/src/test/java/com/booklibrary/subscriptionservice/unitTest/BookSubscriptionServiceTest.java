package com.booklibrary.subscriptionservice.unitTest;

import com.booklibrary.subscriptionservice.model.BookSubscription;
import com.booklibrary.subscriptionservice.repository.BookSubscriptionRepository;
import com.booklibrary.subscriptionservice.service.BookSubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookSubscriptionServiceTest {

    @InjectMocks
    private BookSubscriptionService mockBookSubscriptionServiceTest;

    @Mock
    private BookSubscriptionRepository mockMookSubscriptionRepository;

    private BookSubscription bookSubscription;

    @Mock
    BookSubscription mockBookSubscriptionEntity;

    @BeforeEach
    void setUp(){
        bookSubscription = new BookSubscription();
        bookSubscription.setBookId("B1212");
        bookSubscription.setSubscriberName("Sam");
    }

    @Test
    void getBookSubscriptionsSubscriberNameNullTest(){
        List<BookSubscription> bookSubscriptionList = new ArrayList<>();
        bookSubscriptionList.add(bookSubscription);
        when(mockMookSubscriptionRepository.findAll()).thenReturn(bookSubscriptionList);

        List<BookSubscription> subscriptionList = mockBookSubscriptionServiceTest.getBookSubscriptions(null);

        assertEquals(1,subscriptionList.size());
        verify(mockMookSubscriptionRepository, never()).findBySubscriberName(anyString());
    }

    @Test
    void getBookSubscriptionsSubscriberNameNotNullTest(){
        List<BookSubscription> bookSubscriptionList = new ArrayList<>();
        bookSubscriptionList.add(bookSubscription);
        when(mockMookSubscriptionRepository.findBySubscriberName(anyString())).thenReturn(bookSubscriptionList);

        List<BookSubscription> subscriptionList = mockBookSubscriptionServiceTest.getBookSubscriptions("Sam");

        assertEquals(1,subscriptionList.size());
        verify(mockMookSubscriptionRepository, never()).findAll();
    }

    @Test
    void addBookSubscriptionTest(){
        when(mockMookSubscriptionRepository.findBySubscriberNameAndBookId(anyString(),anyString())).thenReturn(null);

        String message = mockBookSubscriptionServiceTest.addBookSubscription(bookSubscription);

        assertEquals("Successful creation of subscription record",message);
        verify(mockMookSubscriptionRepository,  times(1)).save(bookSubscription);
    }

    @Test
    void addBookSubscriptionWhenAlreadyAddedTest(){
        when(mockMookSubscriptionRepository.findBySubscriberNameAndBookId(anyString(),anyString())).thenReturn(bookSubscription);

        String message = mockBookSubscriptionServiceTest.addBookSubscription(bookSubscription);

        assertEquals("Already added subscription record",message);
        verify(mockMookSubscriptionRepository,  never()).save(bookSubscription);
    }

    @Test
    void updateBookSubscriptionDateReturnedTest(){
        bookSubscription.setDateReturned(LocalDate.now());
        when(mockMookSubscriptionRepository.findBySubscriberNameAndBookId(anyString(),anyString())).thenReturn(mockBookSubscriptionEntity);
        when(mockBookSubscriptionEntity.getDateReturned()).thenReturn(null);

        String message = mockBookSubscriptionServiceTest.updateBookSubscriptionDateReturned(bookSubscription);

        assertEquals("Updated successfully subscription record",message);
        verify(mockMookSubscriptionRepository,  times(1)).save(mockBookSubscriptionEntity);
        verify(mockBookSubscriptionEntity,  times(1)).setDateReturned(LocalDate.now());
    }

    @Test
    void updateBookSubscriptionDateReturnedButDateReturnedAlreadyUpdatedTest(){
        when(mockMookSubscriptionRepository.findBySubscriberNameAndBookId(anyString(),anyString())).thenReturn(mockBookSubscriptionEntity);
        when(mockBookSubscriptionEntity.getDateReturned()).thenReturn(LocalDate.now());

        String message = mockBookSubscriptionServiceTest.updateBookSubscriptionDateReturned(bookSubscription);

        assertEquals("Already Updated Returned Date in subscription record",message);
        verify(mockMookSubscriptionRepository,  never()).save(mockBookSubscriptionEntity);
        verify(mockBookSubscriptionEntity,  never()).setDateReturned(LocalDate.now());
    }

    @Test
    void updateBookSubscriptionDateReturnedButNoRecordPresentTest(){
        when(mockMookSubscriptionRepository.findBySubscriberNameAndBookId(anyString(),anyString())).thenReturn(null);

        String message = mockBookSubscriptionServiceTest.updateBookSubscriptionDateReturned(bookSubscription);

        assertEquals("No subscription record found",message);

        verify(mockBookSubscriptionEntity,  never()).getDateReturned();
        verify(mockMookSubscriptionRepository,  never()).save(mockBookSubscriptionEntity);
        verify(mockBookSubscriptionEntity,  never()).setDateReturned(LocalDate.now());
    }
}
