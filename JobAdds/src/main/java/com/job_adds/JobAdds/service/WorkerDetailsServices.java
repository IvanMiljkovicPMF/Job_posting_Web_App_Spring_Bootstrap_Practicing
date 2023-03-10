package com.job_adds.JobAdds.service;

import com.job_adds.JobAdds.entity.Worker;
import com.job_adds.JobAdds.repository.WorkerRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@NoArgsConstructor
@AllArgsConstructor
@Service
@Transactional
public class WorkerDetailsServices implements UserDetailsService {
    @Autowired
    private WorkerRepo repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Worker worker = repository.findWorkerByEmail(username);
        if(worker==null)
        {
            throw new UsernameNotFoundException("Worker not found");
        }
        return new WorkerDetails(worker);
    }


}
