package com.booklibrary.subscriptionservice.repository;

import com.booklibrary.subscriptionservice.model.BookSubscription;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookSubscriptionRepository extends JpaRepositoryImplementation<BookSubscription,Long> {
    List<BookSubscription> findBySubscriberName(String subscriberName);

    BookSubscription findBySubscriberNameAndBookId(String subscriberName, String bookId);
}
