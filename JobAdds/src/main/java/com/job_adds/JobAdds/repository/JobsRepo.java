package com.job_adds.JobAdds.repository;

import com.job_adds.JobAdds.entity.Job_Posting;
import jakarta.transaction.Transactional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepo extends JpaRepository<Job_Posting,Integer> {

    @Query(value = "select * from jobposting j where j.jobname like %:keyword% ",nativeQuery = true)
    List<Job_Posting> findByKeyword(@Param("keyword") String keyword);
}
