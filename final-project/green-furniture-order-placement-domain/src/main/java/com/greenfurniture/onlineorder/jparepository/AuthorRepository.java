package com.greenfurniture.onlineorder.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenfurniture.onlineorder.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	Author findOneById(Long id);
}
