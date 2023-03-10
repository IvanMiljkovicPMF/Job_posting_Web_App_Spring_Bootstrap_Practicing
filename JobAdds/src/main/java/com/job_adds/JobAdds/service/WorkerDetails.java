package com.job_adds.JobAdds.service;

import com.job_adds.JobAdds.entity.Company;
import com.job_adds.JobAdds.entity.Worker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class WorkerDetails implements UserDetails {

    private Worker worker;

    public WorkerDetails(Worker worker) {
        this.worker = worker;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return worker.getPassword();
    }

    @Override
    public String getUsername() {
        return worker.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public String getWorkerName() {
        return worker.getNamesurname();
    }
    public void setWorkerName(String name) {
        this.worker.setNamesurname(name);
    }
    public int getWorkerID() {
        return worker.getId();
    }

}
