package com.job_adds.JobAdds.repository;

import com.job_adds.JobAdds.entity.LikePosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikePostsRepo extends JpaRepository<LikePosts,Integer> {

    public Optional<LikePosts> findByIdjobAndIdworker(Integer id1,Integer id2);



}
