package com.greenfurniture.onlineorder.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenfurniture.onlineorder.domain.JobPosting;

public interface JobPostingRepository extends JpaRepository<JobPosting,Integer>{
	
	JobPosting findOneById(Integer id);
	
	

}
