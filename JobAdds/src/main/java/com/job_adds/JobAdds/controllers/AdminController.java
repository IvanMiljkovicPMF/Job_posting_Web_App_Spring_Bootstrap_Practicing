package com.job_adds.JobAdds.controllers;

import com.job_adds.JobAdds.entity.*;
import com.job_adds.JobAdds.repository.*;
import com.job_adds.JobAdds.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminDetailsService adminService;
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
    @GetMapping("/admin/home_admin")
    public String AdminHomePage()
    {
        return "admin/home_admin";
    }

    //Register & Login
    @GetMapping("/register/register_admin")
    public String showAdminRegistrationForm(Model model) {
        model.addAttribute("admin", new Admin());

        return "register/register_admin";
    }
    @PostMapping("/process_register2")
    public String processAdminRegister(Admin admin) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);

        adminService.Save(admin);

        return "register/process_register";
    }
    @GetMapping("/admin/admin_login")
    public String viewAdminLoginPage() {
        return "admin/admin_login";
    }

    //List companies, jobs and workers
    @GetMapping("/admin/companies")
    public String viewAdminListCompanies(Model model) {
        List<Company> listCompanies = companyServices.findAll();
        model.addAttribute("listCompanies", listCompanies);

        return "admin/companies";
    }
    @GetMapping("/admin/jobs")
    public String viewAdminListJobs(Model model) {
        List<Job_Posting> listJobs = jobService.findAll();
        model.addAttribute("listJobs", listJobs);
        List<Company> listCompanies = companyServices.findAll();
        model.addAttribute("listCompanies", listCompanies);

        return "admin/jobs";
    }
    @GetMapping("/admin/workers")
    public String viewAdminListWorkers(Model model) {
        List<Worker> listWorkers = workerServices.findAll();
        model.addAttribute("listWorkers", listWorkers);

        return "admin/workers";
    }

    //Job details
    @GetMapping("/admin/jobs/jobdetails/{id}")
    public String showJobDetails(@PathVariable("id") int id, Model model) {
        Job_Posting job_posting = jobService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        model.addAttribute("jobDetails", job_posting);
        return "admin/jobdetails";
    }

    //Company details
    @GetMapping("/admin/companies/companydetails/{id}")
    public String showCompanyDetails(@PathVariable("id") int id, Model model) {
        Company company = companyServices.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + id));
        model.addAttribute("companyDetails", company);
        return "admin/companydetails";
    }

    //Edit admin information
    @GetMapping("/admin/edit/edit_admin/{id}")
    public String showUpdateAdminForm(@PathVariable("id") int id, Model model,@AuthenticationPrincipal AdminDetails adminDetails) {
        Admin admin = adminService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin Id:" + id));
        String email=adminDetails.getUsername();
        Admin admin1 = adminService.findAdminByEmail(email);
        model.addAttribute("admin", admin1);
        model.addAttribute("adminUpdate", admin);
        return "admin/edit/edit_admin";
    }

    // Submit update company
    @PostMapping("/admin/update/{id}")
    public String updateAdmin(@PathVariable("id") int id, @Valid Admin admin, @AuthenticationPrincipal AdminDetails adminDetails, BindingResult result, Model model) {
        if (result.hasErrors()) {
            admin.setId(id);
            return "admin/edit/edit_admin";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);
        adminService.Save(admin);
        adminDetails.setAdminName(admin.getAdminusername());
        return "redirect:/admin/home_admin";
    }

    //All jobs for edit and delete
    @GetMapping("/admin/all/all_jobs")
    public String showCompanyListJob(Model model) {
        List<Job_Posting> listJobs = jobService.findAll();
        model.addAttribute("listJobs", listJobs);
        return "admin/all/all_jobs";
    }

    //Edit a job
    @GetMapping("/admin/edit/edit_job/{id}")
    public String showUpdateAdminJobForm(@PathVariable("id") int id, Model model) {
        Job_Posting job_posting = jobService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin job Id:" + id));
        model.addAttribute("adminJobUpdate", job_posting);
        return "admin/edit/edit_job";
    }

    // Submit update job
    @PostMapping("/admin/update_job/{id}")
    public String updateAdminJob(@PathVariable("id") int id, @Valid Job_Posting jobPosting, BindingResult result, Model model) {
        if (result.hasErrors()) {
            jobPosting.setId(id);
            return "admin/edit/edit_job";
        }
        jobService.Save(jobPosting);
        return "redirect:/admin/all/all_jobs";
    }

    //Delete job
    @GetMapping("/admin/all/all_jobs/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        Job_Posting job_posting = jobService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job delete Id:" + id));
        jobService.Delete(job_posting);
        return "redirect:/admin/all/all_jobs";
    }

    //All companies for edit and delete
    @GetMapping("/admin/all/all_companies")
    public String showAdminCompaniesList(Model model) {
        List<Company> listCompanies = companyServices.findAll();
        model.addAttribute("listCompanies", listCompanies);
        return "admin/all/all_companies";
    }

    //Add company
    @GetMapping("/admin/add/add_company")
    public String showAdminCompanyAddForm(Model model) {

        model.addAttribute("new_company", new Company());
        return "admin/add/add_company";
    }

    //Submit save company
    @PostMapping("/admin/save_company")
    public String saveCompany(@Valid Company company, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "admin/add/add_company";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(company.getPassword());
        company.setPassword(encodedPassword);
        companyServices.Save(company);

        return "redirect:/admin/all/all_companies";
    }

    //Edit a company
    @GetMapping("/admin/edit/edit_company/{id}")
    public String showUpdateAdminCompanyForm(@PathVariable("id") int id, Model model) {
        Company company = companyServices.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin company Id:" + id));
        model.addAttribute("adminCompanyUpdate", company);
        return "admin/edit/edit_company";
    }

    // Submit update company
    @PostMapping("/admin/update_company/{id}")
    public String updateAdminCompany(@PathVariable("id") int id, @Valid Company company, BindingResult result, Model model) {
        if (result.hasErrors()) {
            company.setId(id);
            return "admin/edit/edit_company";
        }
        companyServices.Save(company);
        return "redirect:/admin/all/all_companies";
    }


    //Delete company
    @GetMapping("/admin/all/all_companies/delete/{id}")
    public String deleteAdminCompany(@PathVariable("id") int id, Model model) {
        Company company = companyServices.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company delete Id:" + id));
        companyServices.Delete(company);
        return "redirect:/admin/all/all_companies";
    }

    //All workers for edit and delete
    @GetMapping("/admin/all/all_workers")
    public String showAdminWorkersList(Model model) {
        List<Worker> listWorkers = workerServices.findAll();
        model.addAttribute("listWorkers", listWorkers);
        return "admin/all/all_workers";
    }

    //Add worker
    @GetMapping("/admin/add/add_worker")
    public String showAdminWorkerAddForm(Model model) {

        model.addAttribute("new_worker", new Worker());
        return "admin/add/add_worker";
    }

    //Submit save worker
    @PostMapping("/admin/save_worker")
    public String saveWorker(@Valid Worker worker, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "admin/add/add_worker";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(worker.getPassword());
        worker.setPassword(encodedPassword);
        workerServices.Save(worker);

        return "redirect:/admin/all/all_workers";
    }

    //Edit a worker
    @GetMapping("/admin/edit/edit_worker/{id}")
    public String showUpdateAdminWorkerForm(@PathVariable("id") int id, Model model) {
        Worker worker = workerServices.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin worker Id:" + id));
        model.addAttribute("adminWorkerUpdate", worker);
        return "admin/edit/edit_worker";
    }

    // Submit update worker
    @PostMapping("/admin/update_worker/{id}")
    public String updateAdminWorker(@PathVariable("id") int id, @Valid Worker worker, BindingResult result, Model model) {
        if (result.hasErrors()) {
            worker.setId(id);
            return "admin/edit/edit_worker";
        }
        workerServices.Save(worker);
        return "redirect:/admin/all/all_workers";
    }

    //Delete worker
    @GetMapping("/admin/all/all_workers/delete/{id}")
    public String deleteAdminWorker(@PathVariable("id") int id, Model model) {
        Worker worker = workerServices.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid worker delete Id:" + id));
        workerServices.Delete(worker);
        return "redirect:/admin/all/all_workers";
    }




}
