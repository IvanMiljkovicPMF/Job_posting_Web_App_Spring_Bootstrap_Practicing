package com.job_adds.JobAdds.service;

import com.job_adds.JobAdds.entity.Job_Posting;
import com.job_adds.JobAdds.entity.LikePosts;
import com.job_adds.JobAdds.entity.Worker;
import com.job_adds.JobAdds.repository.JobsRepo;
import com.job_adds.JobAdds.repository.LikePostsRepo;
import com.job_adds.JobAdds.repository.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikePostService {

    @Autowired
    LikePostsRepo likePostsRepo;

    public Optional<LikePosts> findById(Integer id1) {
        return likePostsRepo.findById(id1);
    }

    public void Save(LikePosts likePosts) {
        likePostsRepo.save(likePosts);
    }

    public List<LikePosts> findAll()
    {
        return likePostsRepo.findAll();
    }

    public Optional<LikePosts> findByIdjobAndIdworker(Integer idJob, Integer idWorker) {
        return likePostsRepo.findByIdjobAndIdworker(idJob,idWorker);
    }

    public void Delete(LikePosts likePosts) {
        likePostsRepo.delete(likePosts);
    }
}
