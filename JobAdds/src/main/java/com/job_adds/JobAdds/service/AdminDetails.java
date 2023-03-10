package com.job_adds.JobAdds.service;

import com.job_adds.JobAdds.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AdminDetails implements UserDetails {
    private Admin admin;

    public AdminDetails(Admin admin) {
        this.admin=admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getEmail();
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
    public String getAdminName() {
        return admin.getAdminusername() +" ";
    }
    public void setAdminName(String name) {
        this.admin.setAdminusername(name);
    }
    public int getAdminID() {
        return admin.getId();
    }
}
