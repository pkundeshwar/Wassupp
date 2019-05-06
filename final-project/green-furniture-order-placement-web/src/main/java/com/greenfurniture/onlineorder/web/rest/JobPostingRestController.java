package com.greenfurniture.onlineorder.web.rest;

import java.net.URI;
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

import com.greenfurniture.onlineorder.domain.JobPosting;
import com.greenfurniture.onlineorder.jparepository.JobPostingRepository;

@RestController
@RequestMapping("/api/rest")
public class JobPostingRestController {

	private static final Logger log = LoggerFactory.getLogger(JobPostingRestController.class);

	@Inject
	JobPostingRepository jobPostingRepository;

	@GetMapping("/job_postings/{id}")
	public ResponseEntity<JobPosting> getOneAuthor(@PathVariable Integer id) throws Exception {
		log.debug("Rest request to get the job posting with id : {}", id);

		return Optional.ofNullable(jobPostingRepository.findOneById(id))
				.map(jp -> new ResponseEntity<>(jp,HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}

	@GetMapping("/job_postings")
	public ResponseEntity<List<JobPosting>> getAllJobPosting() {
		log.debug("Rest request to get the job posting with id : {}");
		List<JobPosting> jps = jobPostingRepository.findAll();
		return new ResponseEntity<>(jobPostingRepository.findAll(), HttpStatus.OK);
		// select * from job_posting;
	}

	@PostMapping("/job_postings")
	public ResponseEntity<Void> createJobPosting(@RequestBody JobPosting jobPosting) throws Exception {
		log.debug("Rest request to create a job posting with id : {}", jobPosting);

		if (jobPosting.getId() != null) {
			return ResponseEntity.badRequest()
					.header("Failure", "New job Posting should not have any id, use put method for update").build();
		}
		JobPosting jp = jobPostingRepository.save(jobPosting);
		return ResponseEntity.created(new URI("/api/rest/postings/" + jp.getId())).build();
	}

	@PutMapping("/job_postings")
	public ResponseEntity<JobPosting> updateJobPosting(@RequestBody JobPosting jobPosting) throws Exception {

		log.debug("Rest request to update a job posting with id : {}", jobPosting.getId());
		if (jobPosting.getId() != null) {
			return new ResponseEntity<>(jobPostingRepository.save(jobPosting), HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest()
					.header("Failure", "existing job posting should have an id, use post method to create").build();

		}
	}

	@DeleteMapping("/job_postings/{id}")
	public ResponseEntity<Void> deleteJobPosting(@PathVariable Integer id) throws Exception {

		log.debug("Rest request to delete a job posting with id", id);
		jobPostingRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
