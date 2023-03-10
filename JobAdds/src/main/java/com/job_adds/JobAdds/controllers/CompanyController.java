package com.job_adds.JobAdds.controllers;

import com.job_adds.JobAdds.entity.Company;
import com.job_adds.JobAdds.entity.Job_Posting;
import com.job_adds.JobAdds.entity.Resume;
import com.job_adds.JobAdds.entity.Worker;
import com.job_adds.JobAdds.repository.CompanyRepo;
import com.job_adds.JobAdds.repository.JobsRepo;
import com.job_adds.JobAdds.repository.ResumeRepo;
import com.job_adds.JobAdds.repository.WorkerRepo;
import com.job_adds.JobAdds.service.*;
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
    private WorkerDetailsServices workerServices;
    @Autowired
    private CompanyDetailsServices companyServices;
    @Autowired
    private JobService jobService;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private LikePostService likePostService;

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

        companyServices.Save(company);
        return "home/process_register";
    }
    @GetMapping("/company/company_login")
    public String viewCompanyLoginPage() {
        return "company/company_login";
    }

    //List companies and jobs
    @GetMapping("/company/companies")
    public String viewCompanyHomePage(Model model) {
        List<Company> listCompanies = companyServices.findAll();
        model.addAttribute("listCompanies", listCompanies);

        return "company/company_companies";
    }
    @GetMapping("/company/jobs")
    public String viewCompanyHomePage1(Model model) {
        List<Job_Posting> listJobs = jobService.findAll();
        model.addAttribute("listJobs", listJobs);
        List<Company> listCompanies = companyServices.findAll();
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

        jobService.Save(jobPosting);

        return "redirect:/company/myjobs";
    }

    //List of company jobs
    @GetMapping("/company/myjobs")
    public String showCompanyListJob(Model model) {
        List<Job_Posting> listJobs = jobService.findAll();
        model.addAttribute("listJobs", listJobs);
        return "company/my_jobs";
    }

    //Edit company information
    @GetMapping("/company/edit/edit_company/{id}")
    public String showUpdateCompanyForm(@PathVariable("id") int id, Model model,@AuthenticationPrincipal CompanyDetails companyDetails) {
        Company company = companyServices.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + id));
        String email=companyDetails.getUsername();
        Company company1 = companyServices.findCompanyByEmail(email);
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
        companyServices.Save(company);
        companyDetails.setCompanyName(company.getCompanyname());
        return "redirect:/company/home_company";
    }

    //Edit a job
    @GetMapping("/company/edit/edit_job/{id}")
    public String showUpdateCompanyJobForm(@PathVariable("id") int id, Model model) {
        Job_Posting job_posting = jobService.findById(id)
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
        jobService.Save(jobPosting);
        return "redirect:/company/myjobs";
    }

    //Delete company job
    @GetMapping("/company/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        Job_Posting job_posting = jobService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company job delete Id:" + id));
        jobService.Delete(job_posting);
        return "redirect:/company/myjobs";
    }

    //Job details
    @GetMapping("/company/jobs/jobdetails/{id}")
    public String showJobDetails(@PathVariable("id") int id, Model model) {
        Job_Posting job_posting = jobService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        model.addAttribute("jobDetails", job_posting);
        return "company/jobdetails";
    }

    //Company details
    @GetMapping("/company/companies/companydetails/{id}")
    public String showCompanyDetails(@PathVariable("id") int id, Model model) {
        Company company = companyServices.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + id));
        model.addAttribute("companyDetails", company);
        return "company/companydetails";
    }

    //List my resumes
    @GetMapping("/company/my_resumes")
    public String viewCompanyListResumes(Model model) {
        List<Resume> listResumes = resumeService.findAll();
        model.addAttribute("listResumes", listResumes);
        List<Job_Posting> listJobs = jobService.findAll();
        model.addAttribute("listJobs", listJobs);
        List<Worker> listWorkers = workerServices.findAll();
        model.addAttribute("listWorkers", listWorkers);

        return "company/my_resumes";
    }
}
