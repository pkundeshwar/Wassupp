package com.greenfurniture.onlineorder.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenfurniture.onlineorder.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
	
	Authority findOneByName(String name);
}
