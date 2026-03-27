package com.ubisam.exam.api.books;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.get;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.Book;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BookTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private BookDocs docs;

  @Autowired
  private BookRepository bookRepository;

  @Test
  void contextLoads() throws Exception{
    // Crud - C
    mvc.perform(post("/api/books").content(docs::newEntity, "나무", "나무 한그루.")).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

    // Crud - R
    String uri = docs.context("entity1", "$._links.self.href");
    mvc.perform(get(uri)).andExpect(is2xx());

    //Crud - U
    Map<String, Object> body = docs.context("entity1", "$");
    mvc.perform(put(uri).content(docs::updateEntity, body, "사과")).andExpect(is2xx());

    //Crud - D
    mvc.perform(delete(uri)).andExpect(is2xx());
  }

  @Test
  void contextLoads2 () throws Exception{

    Specification<Book> spec;
    List<Book> result;
    boolean hasResult;

    // 40개의 책 추가
    List<Book> bookLists = new ArrayList<>();
    for(int i = 1; i <= 40; i++){
      bookLists.add(docs.newEntity("나무"+i, "나무"+i+"그루."));
    }
    bookRepository.saveAll(bookLists);

    JpaSpecificationBuilder<Book> nameQuery = JpaSpecificationBuilder.of(Book.class);
    spec = nameQuery.where().and().eq("bookTitle", "나무3").build();
    result = bookRepository.findAll(spec);
    hasResult = result.stream().anyMatch(u -> "나무3".equals(u.getBookTitle()));
    assertEquals(true, hasResult);

    JpaSpecificationBuilder<Book> contentQuery = JpaSpecificationBuilder.of(Book.class);
    spec = contentQuery.where().and().eq("bookContent", "나무5그루.").build();
    result = bookRepository.findAll(spec);
    hasResult = result.stream().anyMatch(u -> "나무5그루.".equals(u.getBookContent()));
    assertEquals(true, hasResult);
  }

  @Test
  void contextLoads3() throws Exception{
    // 40개의 책 추가
    List<Book> bookLists = new ArrayList<>();
    for(int i = 1; i <= 40; i++){
      bookLists.add(docs.newEntity("사과"+i, "사과"+i+"개."));
    }
    bookRepository.saveAll(bookLists);

    // Search - 단일 검색
    mvc.perform(get("/api/books/search/findByBookTitle").param("bookTitle", "사과5")).andExpect(is2xx());
    mvc.perform(get("/api/books/search/findByBookContent").param("bookContent", "사과2개.")).andExpect(is2xx());

    // Search - 페이지네이션 8개씩 5페이지
    mvc.perform(get("/api/books").param("size", "8")).andExpect(is2xx());

    // Search - 정렬 title,desc
    mvc.perform(get("/api/books").param("sort", "bookTitle,desc")).andExpect(is2xx());
  }


  
}
