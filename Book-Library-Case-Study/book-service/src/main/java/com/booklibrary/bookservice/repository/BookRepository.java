package com.booklibrary.bookservice.repository;

import com.booklibrary.bookservice.model.Book;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;



@Repository
public interface BookRepository extends JpaRepositoryImplementation<Book,Long> {
}
