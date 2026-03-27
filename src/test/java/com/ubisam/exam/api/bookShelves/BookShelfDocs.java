package com.ubisam.exam.api.bookShelves;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.BookShelf;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BookShelfDocs extends MockMvcRestDocs{
  //목적: 이름, 위치 필드를 사용하여 테스트하기 위한 메소드
  public BookShelf newEntity(String... entity) {
    BookShelf body = new BookShelf();
    body.setBookShelfName(entity.length > 0 ? entity[0] : super.randomText("bookShelfName"));
    body.setBookShelfLocation(entity.length > 1 ? entity[1] : super.randomText("bookShelfLocation"));
    return body;
  }
  
  //목적: 이름 변경 후 테스트를 위한 메소드
  public Map<String, Object> updateEntity(Map<String, Object> entity, String body){
    entity.put("bookShelfName", body);
    return entity;
  }

  //목적: searchName 통한 검색
  public Map<String, Object> setSearchName(String search){
    Map<String, Object> entity = new HashMap<>();
    entity.put("searchName", search);
    return entity;
  }

  //목적: searchLocation 통한 검색
  public Map<String, Object> setSearchLocation(String search){
    Map<String, Object> entity = new HashMap<>();
    entity.put("searchLocation", search);
    return entity;
  }
}
