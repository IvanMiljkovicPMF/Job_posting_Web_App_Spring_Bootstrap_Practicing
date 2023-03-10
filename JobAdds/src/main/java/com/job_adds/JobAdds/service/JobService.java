package com.job_adds.JobAdds.service;

import com.job_adds.JobAdds.entity.Job_Posting;
import com.job_adds.JobAdds.repository.JobsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {


    @Autowired
    private JobsRepo jobsRepo;

    public Optional<Job_Posting> findById(Integer id) {
        return jobsRepo.findById(id);
    }

    public void save(Job_Posting jobPosting) {
        jobsRepo.save(jobPosting);
    }

    public List<Job_Posting> findByKeyword(String keyword)
    {
        return jobsRepo.findByKeyword(keyword);
    }

//    public void likeJob(int jobPosting){
//
//        jobsRepo.likesJob_Posting(jobPosting);
//    }

//    public void unlikeJob(Job_Posting jobPosting){
//        jobsRepo.unlikesPost(jobPosting.getId());
//    }
}
