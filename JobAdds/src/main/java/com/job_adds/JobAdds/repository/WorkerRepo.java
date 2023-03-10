package com.job_adds.JobAdds.repository;

import com.job_adds.JobAdds.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepo extends JpaRepository<Worker,Integer> {
    public Worker findWorkerByEmail(String email);
}
