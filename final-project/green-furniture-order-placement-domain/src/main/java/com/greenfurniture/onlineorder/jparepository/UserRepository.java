package com.greenfurniture.onlineorder.jparepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greenfurniture.onlineorder.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findOneById(Long id);
	Optional<User> findOneByActivationKey(String activationKey);
	List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);
	Optional<User> findOneByResetKey(String resetKey);
	Optional<User> findOneByEmail(String email);
	Optional<User> findOneByLogin(String login);

	@EntityGraph(attributePaths = "authorities")
	User findOneWithAuthoritiesById(Long id);

	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByLogin(String login);
	Page<User> findAllByLoginNot(Pageable pageable, String login);
}
