package com.booklibrary.subscriptionservice.service;

import com.booklibrary.subscriptionservice.model.BookSubscription;
import com.booklibrary.subscriptionservice.repository.BookSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookSubscriptionService {

    @Autowired
    BookSubscriptionRepository bookSubscriptionRepository;

    public List<BookSubscription> getBookSubscriptions(String subscriberName){
        if(subscriberName == null || subscriberName.isEmpty())
            return bookSubscriptionRepository.findAll();
        else
            return bookSubscriptionRepository.findBySubscriberName(subscriberName);
    }

    @Transactional
    public String addBookSubscription(BookSubscription bookSubscription){
        if(IsBookSubscriptionAvailable(bookSubscription.getSubscriberName(), bookSubscription.getBookId()) ) {
            bookSubscriptionRepository.save(bookSubscription);
            return "Successful creation of subscription record";
        }
        return "Already added subscription record";
    }

    public String updateBookSubscriptionDateReturned(BookSubscription bookSubscription) {
        if(!IsBookSubscriptionAvailable(bookSubscription.getSubscriberName(), bookSubscription.getBookId()) ) {
            BookSubscription bookSubscriptionEntity = bookSubscriptionRepository.findBySubscriberNameAndBookId(bookSubscription.getSubscriberName(), bookSubscription.getBookId());
            if (bookSubscriptionEntity.getDateReturned() == null) {
                bookSubscriptionEntity.setDateReturned(bookSubscription.getDateReturned());
                bookSubscriptionRepository.save(bookSubscriptionEntity);
                return "Updated successfully subscription record";
            } else
                return "Already Updated Returned Date in subscription record";
        }
        else
            return "No subscription record found";
    }

    public boolean IsBookSubscriptionAvailable(String subscriberName, String bookId) {
        BookSubscription bookSubscriptionEntity = bookSubscriptionRepository.findBySubscriberNameAndBookId(subscriberName, bookId);
        if(bookSubscriptionEntity == null)
            return true;
        return false;
    }
}
