package com.job_adds.JobAdds.service;

import com.job_adds.JobAdds.entity.Company;
import com.job_adds.JobAdds.entity.Worker;
import com.job_adds.JobAdds.repository.CompanyRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class CompanyDetailsServices implements UserDetailsService {

    @Autowired
    private CompanyRepo repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Company company = repository.findCompanyByEmail(username);
        if(company==null)
        {
            throw new UsernameNotFoundException("Company not found");
        }
        return new CompanyDetails(company);
    }
    public Company findCompanyByEmail(String email)
    {
        return repository.findCompanyByEmail(email);
    }

    public void Save(Company company)
    {
        repository.save(company);
    }

    public Optional<Company> findById(Integer id)
    {
        return repository.findById(id);
    }
    public List<Company> findAll()
    {
        return repository.findAll();
    }

    public void Delete(Company company) {
        repository.delete(company);
    }
}
