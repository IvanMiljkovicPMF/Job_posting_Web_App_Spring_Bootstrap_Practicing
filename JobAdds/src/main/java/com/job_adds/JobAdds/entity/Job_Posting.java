package com.job_adds.JobAdds.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="jobposting")
public class Job_Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Column(name = "idcompany")
    private Integer idcompany;
    @Column(name = "workhome")
    private int workhome;

    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    //@DateTimeFormat(pattern = "yyyy-MM-dd")

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date date;
    @Column(name = "description")
    private String description;
    @Column(name = "likes", columnDefinition = "integer default 0")
    private int likes;

    @Column(name = "jobname", length = 100)
    private String jobname;
    @Column(name = "slogan", length = 100)
    private String slogan;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(Integer idcompany) {
        this.idcompany = idcompany;
    }

    public int getWorkhome() {
        return workhome;
    }

    public void setWorkhome(int workhome) {
        this.workhome = workhome;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
