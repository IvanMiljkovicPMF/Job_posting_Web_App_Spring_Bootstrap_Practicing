package com.job_adds.JobAdds.repository;

import com.job_adds.JobAdds.entity.Admin;
import com.job_adds.JobAdds.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {

    public Admin findAdminByEmail(String email);
}
