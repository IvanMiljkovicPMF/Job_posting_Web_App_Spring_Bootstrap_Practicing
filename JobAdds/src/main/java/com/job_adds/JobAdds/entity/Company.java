package com.job_adds.JobAdds.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email",unique = true, length = 100)
    private String email;

    private String password;
    @Column(name = "description")
    private String description;
    @Column(name = "location", length = 100)
    private String location;
    @Column(name = "contact", length = 100)
    private String contact;
    @Column(name = "website", length = 100)
    private String website;
    @Column(name = "PIB", length = 100)
    private String PIB;
    @Column(name = "companyname", length = 100)
    private String companyname;
    @Column(name = "companyworkers", length = 100)
    private String companyworkers;
    @Column(name = "adress", length = 100)
    private String adress;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPIB() {
        return PIB;
    }

    public void setPIB(String PIB) {
        this.PIB = PIB;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyworkers() {
        return companyworkers;
    }

    public void setCompanyworkers(String companyworkers) {
        this.companyworkers = companyworkers;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
