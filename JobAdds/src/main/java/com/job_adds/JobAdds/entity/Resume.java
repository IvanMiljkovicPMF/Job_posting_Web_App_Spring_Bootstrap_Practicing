package com.job_adds.JobAdds.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "jobid")
    private Integer jobid;

    @Column(name = "jobseekerid")
    private Integer jobseekerid;

    @Column(name = "resumepath" ,length = 100)
    private String resumepath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobid() {
        return jobid;
    }

    public void setJobid(Integer jobid) {
        this.jobid = jobid;
    }

    public Integer getJobseekerid() {
        return jobseekerid;
    }

    public void setJobseekerid(Integer jobseekerid) {
        this.jobseekerid = jobseekerid;
    }


    public String getResumepath() {
        return resumepath;
    }

    public void setResumepath(String resumepath) {
        this.resumepath = resumepath;
    }
}
