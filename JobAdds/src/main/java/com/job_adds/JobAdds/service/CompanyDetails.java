package com.job_adds.JobAdds.service;

import com.job_adds.JobAdds.entity.Company;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CompanyDetails implements UserDetails {
    private Company company;
    public CompanyDetails(Company company) {
        this.company = company;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return company.getPassword();
    }

    @Override
    public String getUsername() {
        return company.getEmail();
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

    public String getCompanyName() {
        return company.getCompanyname() +" ";
    }
    public void setCompanyName(String name) {
        this.company.setCompanyname(name);
    }

    public int getCompanyID() {
        return company.getId();
    }
}
