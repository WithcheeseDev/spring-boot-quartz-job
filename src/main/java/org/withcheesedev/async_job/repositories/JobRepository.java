package org.withcheesedev.async_job.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.withcheesedev.async_job.models.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    @Query(value="SELECT J FROM Job J ORDER BY J.createdDate DESC LIMIT 1")
    Job findJobByOrderByCreatedDate();
}
