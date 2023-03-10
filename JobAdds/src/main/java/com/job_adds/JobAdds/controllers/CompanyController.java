package com.job_adds.JobAdds.controllers;

import com.job_adds.JobAdds.entity.Company;
import com.job_adds.JobAdds.entity.Job_Posting;
import com.job_adds.JobAdds.entity.Resume;
import com.job_adds.JobAdds.entity.Worker;
import com.job_adds.JobAdds.repository.CompanyRepo;
import com.job_adds.JobAdds.repository.JobsRepo;
import com.job_adds.JobAdds.repository.ResumeRepo;
import com.job_adds.JobAdds.repository.WorkerRepo;
import com.job_adds.JobAdds.service.CompanyDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CompanyController {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private WorkerRepo workerRepo;
    @Autowired
    private JobsRepo jobsRepo;
    @Autowired
    private ResumeRepo resumeRepo;

    //Home
    @GetMapping("/company/home_company")
    public String CompanyHomePage()
    {
        return "company/home_company";
    }

    //Register & Login
    @GetMapping("/register/register_company")
    public String showCompanyRegistrationForm(Model model) {
        model.addAttribute("company", new Company());

        return "register/register_company";
    }
    @PostMapping("/process_register1")
    public String processCompanyRegister(Company company) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(company.getPassword());
        company.setPassword(encodedPassword);

        companyRepo.save(company);
        return "home/process_register";
    }
    @GetMapping("/company/company_login")
    public String viewCompanyLoginPage() {
        return "company/company_login";
    }

    //List companies and jobs
    @GetMapping("/company/companies")
    public String viewCompanyHomePage(Model model) {
        List<Company> listCompanies = companyRepo.findAll();
        model.addAttribute("listCompanies", listCompanies);

        return "company/company_companies";
    }
    @GetMapping("/company/jobs")
    public String viewCompanyHomePage1(Model model) {
        List<Job_Posting> listJobs = jobsRepo.findAll();
        model.addAttribute("listJobs", listJobs);
        List<Company> listCompanies = companyRepo.findAll();
        model.addAttribute("listCompanies", listCompanies);
        return "company/company_jobs";
    }

    //Add job
    @GetMapping("/company/add/add_job")
    public String showCompanyJobAddForm(Model model) {

        model.addAttribute("new_job", new Job_Posting());
        return "company/add/add_job";
    }

    //Submit save job
    @PostMapping("/company/save")
    public String saveCompanyJob(@Validated Job_Posting jobPosting, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "company/add/add_job";
        }

        jobsRepo.save(jobPosting);

        return "redirect:/company/myjobs";
    }

    //List of company jobs
    @GetMapping("/company/myjobs")
    public String showCompanyListJob(Model model) {
        List<Job_Posting> listJobs = jobsRepo.findAll();
        model.addAttribute("listJobs", listJobs);
        return "company/my_jobs";
    }

    //Edit company information
    @GetMapping("/company/edit/edit_company/{id}")
    public String showUpdateCompanyForm(@PathVariable("id") int id, Model model,@AuthenticationPrincipal CompanyDetails companyDetails) {
        Company company = companyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + id));
        String email=companyDetails.getUsername();
        Company company1 = companyRepo.findCompanyByEmail(email);
        model.addAttribute("company", company1);
        model.addAttribute("companyUpdate", company);
        return "company/edit/edit_company";
    }

    // Submit update company
    @PostMapping("/company/update/{id}")
    public String updateCompany(@PathVariable("id") int id, @Valid Company company,@AuthenticationPrincipal CompanyDetails companyDetails, BindingResult result, Model model) {
        if (result.hasErrors()) {
            company.setId(id);
            return "company/edit/edit_company";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(company.getPassword());
        company.setPassword(encodedPassword);
        companyRepo.save(company);
        companyDetails.setCompanyName(company.getCompanyname());
        return "redirect:/company/home_company";
    }

    //Edit a job
    @GetMapping("/company/edit/edit_job/{id}")
    public String showUpdateCompanyJobForm(@PathVariable("id") int id, Model model) {
        Job_Posting job_posting = jobsRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company job Id:" + id));
        model.addAttribute("companyJobUpdate", job_posting);
        return "company/edit/edit_job";
    }

    // Submit update job
    @PostMapping("/company/update_job/{id}")
    public String updateCompanyJob(@PathVariable("id") int id, @Valid Job_Posting jobPosting, BindingResult result, Model model) {
        if (result.hasErrors()) {
            jobPosting.setId(id);
            return "company/edit/edit_job";
        }
        jobsRepo.save(jobPosting);
        return "redirect:/company/myjobs";
    }

    //Delete company job
    @GetMapping("/company/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        Job_Posting job_posting = jobsRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company job delete Id:" + id));
        jobsRepo.delete(job_posting);
        return "redirect:/company/myjobs";
    }

    //Job details
    @GetMapping("/company/jobs/jobdetails/{id}")
    public String showJobDetails(@PathVariable("id") int id, Model model) {
        Job_Posting job_posting = jobsRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        model.addAttribute("jobDetails", job_posting);
        return "company/jobdetails";
    }

    //Company details
    @GetMapping("/company/companies/companydetails/{id}")
    public String showCompanyDetails(@PathVariable("id") int id, Model model) {
        Company company = companyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + id));
        model.addAttribute("companyDetails", company);
        return "company/companydetails";
    }

    //List my resumes
    @GetMapping("/company/my_resumes")
    public String viewCompanyListResumes(Model model) {
        List<Resume> listResumes = resumeRepo.findAll();
        model.addAttribute("listResumes", listResumes);
        List<Job_Posting> listJobs = jobsRepo.findAll();
        model.addAttribute("listJobs", listJobs);
        List<Worker> listWorkers = workerRepo.findAll();
        model.addAttribute("listWorkers", listWorkers);

        return "company/my_resumes";
    }
}
