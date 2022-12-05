package com.booklibrary.subscriptionservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private long id;

    private String bookId;

    private String bookName;

    private String author;

    private int availableCopies;

    private int totalCopies;
}
