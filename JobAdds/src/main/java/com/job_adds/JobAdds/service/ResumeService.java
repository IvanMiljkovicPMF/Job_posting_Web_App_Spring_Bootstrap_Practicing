package com.job_adds.JobAdds.service;

import com.job_adds.JobAdds.entity.LikePosts;
import com.job_adds.JobAdds.entity.Resume;
import com.job_adds.JobAdds.repository.LikePostsRepo;
import com.job_adds.JobAdds.repository.ResumeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {
    @Autowired
    ResumeRepo resumeRepo;
    public List<Resume> findAll()
    {
        return resumeRepo.findAll();
    }

    public void Save(Resume resume) {
        resumeRepo.save(resume);
    }
}
