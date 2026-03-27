package com.ubisam.exam.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "example_bookShelf")
public class BookShelf {

  @Id
  @GeneratedValue
  private Long id;

  private String bookShelfName;
  private String bookShelfLocation;
  
  // @OneToMany
  // private List<Book> books;

  @Transient
  private String searchName;

  @Transient
  private String searchLocation;

}
