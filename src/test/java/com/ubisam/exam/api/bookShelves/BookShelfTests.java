package com.ubisam.exam.api.bookShelves;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.result;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.Book;
import com.ubisam.exam.domain.BookShelf;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BookShelfTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private BookShelfDocs docs;

  @Autowired
  private BookShelfRepository bookShelfRepository;

  @Test
  void contextLoads() throws Exception{
    // Crud - C
    mvc.perform(post("/api/bookShelves").content(docs::newEntity, "역사", "1-1")).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

    // Crud - R
    String uri = docs.context("entity1", "$._links.self.href");
    mvc.perform(post(uri)).andExpect(is2xx());

    // Crud - U
    Map<String, Object> body = docs.context("entity1", "$");
    mvc.perform(put(uri).content(docs::updateEntity, body, "수학")).andExpect(is2xx());

    // Crud - D
    mvc.perform(delete(uri)).andExpect(is2xx());
  }

  @Test
  void contextLoads2() throws Exception{
    List<BookShelf> result;
    boolean hasResult;

    // 40개의 엔티티 추가
    List<BookShelf> bookShelfLists = new ArrayList<>();
    for(int i = 1; i <= 40; i++){
      bookShelfLists.add(docs.newEntity("역사"+i, i+"-1"));
    }
    bookShelfRepository.saveAll(bookShelfLists);

    JpaSpecificationBuilder<BookShelf> nameQuery = JpaSpecificationBuilder.of(BookShelf.class);
    nameQuery.where().and().eq("bookShelfName", "역사5");
    result = bookShelfRepository.findAll(nameQuery.build());
    hasResult = result.stream().anyMatch(u -> "역사5".equals(u.getBookShelfName()));
    assertEquals(true, hasResult);

    JpaSpecificationBuilder<BookShelf> locationQuery = JpaSpecificationBuilder.of(BookShelf.class);
    locationQuery.where().and().eq("bookShelfLocation", "1-1");
    result = bookShelfRepository.findAll(locationQuery.build());
    hasResult = result.stream().anyMatch(u -> "1-1".equals(u.getBookShelfLocation()));
    assertEquals(true, hasResult);
  }

  @Test
  void contextLoads3() throws Exception{

    // 40개의 엔티티 추가
    List<BookShelf> bookShelfLists = new ArrayList<>();
    for(int i = 1; i <= 40; i++){
      bookShelfLists.add(docs.newEntity("역사"+i, i+"-1"));
    }
    bookShelfRepository.saveAll(bookShelfLists);

    String uri = "/api/bookShelves/search";
    // Search - 단일 검색
    mvc.perform(post(uri).content(docs::setSearchName, "역사5")).andExpect(is2xx());
    mvc.perform(post(uri).content(docs::setSearchLocation, "2-1")).andExpect(is2xx());

    // Search - 페이지네이션 5개씩 8페이지
    mvc.perform(post(uri).content(docs::setSearchName, "").param("size", "5")).andExpect(is2xx());

    // Search - 정렬 name,desc
    mvc.perform(post(uri).content(docs::setSearchName, "").param("sort", "bookShelfName,desc")).andExpect(is2xx());
  }
  
}
