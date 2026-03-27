package com.ubisam.exam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "example_book")
public class Book {

  @Id
  @GeneratedValue
  private Long id;

  private String bookTitle;
  private String bookContent;
  private String bookAuthor;
  private String bookCategory;

  // @ManyToOne
  // private BookShelf bookShelf;

  
}
