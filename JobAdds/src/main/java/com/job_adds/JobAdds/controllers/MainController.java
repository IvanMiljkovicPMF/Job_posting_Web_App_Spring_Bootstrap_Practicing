package com.job_adds.JobAdds.controllers;

import com.job_adds.JobAdds.entity.Company;
import com.job_adds.JobAdds.entity.Job_Posting;
import com.job_adds.JobAdds.entity.Worker;
import com.job_adds.JobAdds.repository.CompanyRepo;
import com.job_adds.JobAdds.repository.JobsRepo;
import com.job_adds.JobAdds.repository.WorkerRepo;
import com.job_adds.JobAdds.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private WorkerDetailsServices workerServices;
    @Autowired
    private CompanyDetailsServices companyServices;
    @Autowired
    private JobService jobService;

    //Home
    @GetMapping("/")
    public String HomePage(Model model)
    {
        List<Job_Posting> listJobs = jobService.findAll();
        model.addAttribute("listJobs", listJobs);
        List<Company> listCompanies = companyServices.findAll();
        model.addAttribute("listCompanies", listCompanies);

        return "home/home";
    }

    //List companies, jobs and workers
    @GetMapping("/home/companies")
    public String listCompanies(Model model) {
        List<Company> listCompanies = companyServices.findAll();
        model.addAttribute("listCompanies", listCompanies);

        return "home/companies";
    }
    @GetMapping("/home/jobadds")
    public String listJobs(Model model, String keyword) {


        if(keyword!=null)
        {
            model.addAttribute("listJobs",jobService.findByKeyword(keyword));
        }
        else
        {
            List<Job_Posting> listJobs = jobService.findAll();
            model.addAttribute("listJobs", listJobs);
        }
        model.addAttribute("keyword",keyword);
        List<Company> listCompanies = companyServices.findAll();
        model.addAttribute("listCompanies", listCompanies);
        return "home/jobs";
    }
    @GetMapping("/home/workers")
    public String listWorkers(Model model) {
        List<Worker> listWorkers = workerServices.findAll();
        model.addAttribute("listWorkers", listWorkers);

        return "home/workers";
    }

    //Job details
    @GetMapping("/home/jobadds/jobdetails/{id}")
    public String showJobDetails(@PathVariable("id") int id, Model model) {
        Job_Posting job_posting = jobService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        model.addAttribute("jobDetails", job_posting);
        return "home/jobdetails";
    }

    //Company details
    @GetMapping("/home/companies/companydetails/{id}")
    public String showCompanyDetails(@PathVariable("id") int id, Model model) {
        Company company = companyServices.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + id));
        model.addAttribute("companyDetails", company);
        return "home/companydetails";
    }
}
