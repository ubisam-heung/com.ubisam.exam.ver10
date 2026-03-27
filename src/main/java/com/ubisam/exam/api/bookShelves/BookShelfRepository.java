package com.ubisam.exam.api.bookShelves;

import com.ubisam.exam.domain.BookShelf;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface BookShelfRepository extends RestfulJpaRepository<BookShelf, Long>{
  
}
