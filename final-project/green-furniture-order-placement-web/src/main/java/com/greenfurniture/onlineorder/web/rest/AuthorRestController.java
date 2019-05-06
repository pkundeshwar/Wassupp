package com.greenfurniture.onlineorder.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenfurniture.onlineorder.domain.Author;
import com.greenfurniture.onlineorder.jparepository.AuthorRepository;

@RestController
@RequestMapping("/api/rest")
public class AuthorRestController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthorRestController.class);

	@Inject
	private AuthorRepository authorRepository;

	/**
	 * POST /authors : Creates a new author.
	 */
	@PostMapping("/authors")
	public ResponseEntity<Void> createAuthor(@RequestBody Author author) throws URISyntaxException {
		LOG.debug("REST request to save Author : {}", author);

		if (author.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new author cannot already have an ID").build();
		}

		Author result = authorRepository.save(author);
		return ResponseEntity.created(new URI("/api/rest/authors/" + result.getId())).build();
	}

	/**
	 * PUT /authors : Update an existing author.
	 */
	@PutMapping("/authors")
	public ResponseEntity<Author> updateAuthor(@RequestBody Author author) throws URISyntaxException {
		LOG.debug("REST request to update Authors : {}", author);
		if (author.getId() != null) {
			return new ResponseEntity<>(authorRepository.save(author), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	/**
	 * GET /authors : Get all authors.
	 */
	@GetMapping("/authors")
	public ResponseEntity<List<Author>> getAllAuthors() throws URISyntaxException {
		LOG.debug("REST request to get all Authors");
		return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
	}

	/**
	 * GET /authors/{id} : Get an author by id.
	 */
	@GetMapping("/authors/{id}")
	public ResponseEntity<Author> getOneAuthorById(@PathVariable Long id) throws URISyntaxException {
		LOG.debug("REST request to get one Author by Id: {}", id);
		return Optional.ofNullable(authorRepository.findOneById(id))
				.map(author -> new ResponseEntity<>(author, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /authors/{id} : Delete an author by id.
	 */
	@DeleteMapping("/authors/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) throws URISyntaxException {
		LOG.debug("REST request to delete Authors with ID : {}", id);
		authorRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
