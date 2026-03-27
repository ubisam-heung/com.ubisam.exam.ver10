package com.ubisam.exam.api.books;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.Book;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BookDocs extends MockMvcRestDocs{
  //목적: 내용, 작성자를 사용하여 테스트하기 위한 메소드
  public Book newEntity(String... entity) {
    Book body = new Book();
    body.setBookTitle(entity.length > 0 ? entity[0] : super.randomText("bookTitle"));
    body.setBookContent(entity.length > 1 ? entity[1] : super.randomText("bookContent"));
    body.setBookCategory(super.randomText("bookCategory"));
    body.setBookAuthor(super.randomText("bookAuthor"));
    return body;
  }
  
  //목적: 제목 변경 후 테스트를 위한 메소드
  public Map<String, Object> updateEntity(Map<String, Object> entity, String body){
    entity.put("bookTitle", body);
    return entity;
  }
}
