package com.job_adds.JobAdds.repository;

import com.job_adds.JobAdds.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepo extends JpaRepository<Resume,Integer> {

}
