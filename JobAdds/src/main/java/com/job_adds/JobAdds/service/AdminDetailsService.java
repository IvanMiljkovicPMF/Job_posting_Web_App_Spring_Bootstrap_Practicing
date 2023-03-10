package com.job_adds.JobAdds.service;

import com.job_adds.JobAdds.entity.Admin;
import com.job_adds.JobAdds.entity.Company;
import com.job_adds.JobAdds.repository.AdminRepo;
import com.job_adds.JobAdds.repository.CompanyRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class AdminDetailsService implements UserDetailsService {
    @Autowired
    private AdminRepo repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = repository.findAdminByEmail(username);
        if(admin==null)
        {
            throw new UsernameNotFoundException("Admin not found");
        }
        return new AdminDetails(admin);
    }

    public void Save(Admin admin) {
        repository.save(admin);
    }

    public Optional<Admin> findById(Integer id) {
        return repository.findById(id);
    }

    public Admin findAdminByEmail(String email) {
        return repository.findAdminByEmail(email);
    }
}
