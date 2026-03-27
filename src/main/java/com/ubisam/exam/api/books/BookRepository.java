package com.ubisam.exam.api.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ubisam.exam.domain.Book;
import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book>{

  List<Book> findByBookTitle(String bookTitle);
  List<Book> findByBookContent(String bookContent);
  
}
