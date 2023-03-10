package com.job_adds.JobAdds.repository;

import com.job_adds.JobAdds.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company,Integer> {

    public Company findCompanyByEmail(String email);
}
