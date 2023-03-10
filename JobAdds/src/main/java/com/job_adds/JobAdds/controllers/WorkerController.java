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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class WorkerController {
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
    @GetMapping("/worker/home_worker")
    public String WorkerHomePage()
    {
        return "worker/home_worker";
    }

    //Register & Login
    @GetMapping("/register/register_worker")
    public String showWorkerRegistrationForm(Model model) {
        model.addAttribute("worker", new Worker());
        return "register/register_worker";
    }
    @PostMapping("/process_register")
    public String processWorkerRegister(@Valid Worker worker, BindingResult bindingResult, Model model) {

        Worker existingUser = workerServices.findWorkerbyEmail(worker.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            bindingResult.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("worker", worker);
            return "register/register_worker";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(worker.getPassword());
        worker.setPassword(encodedPassword);

        workerServices.Save(worker);

        return "home/process_register";
    }
    @GetMapping("/worker/worker_login")
    public String viewWorkerLoginPage() {

        return "worker/worker_login";
    }

    //List companies and jobs
    @GetMapping("/worker/companies")
    public String viewWorkerListCompanies(Model model) {
        List<Company> listCompanies = companyServices.findAll();
        model.addAttribute("listCompanies", listCompanies);

        return "worker/worker_companies";
    }
    @GetMapping("/worker/jobs")
    public String viewWorkerListJobs(Model model) {
        List<Job_Posting> listJobs = jobService.findAll();
        model.addAttribute("listJobs", listJobs);
        List<Company> listCompanies = companyServices.findAll();
        model.addAttribute("listCompanies", listCompanies);
        List<LikePosts> listLikes = likePostService.findAll();
        model.addAttribute("listLikes", listLikes);

        return "worker/worker_jobs";
    }

    //Edit worker information
    @GetMapping("/worker/edit/edit_worker/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model, @AuthenticationPrincipal WorkerDetails workerDetails) {
        Integer ID= Integer.valueOf(id);
        Worker worker = workerServices.findById(ID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid worker Id:" + id));

        String email=workerDetails.getUsername();
        Worker worker1 = workerServices.findWorkerbyEmail(email);
        model.addAttribute("worker", worker1);
        model.addAttribute("workerUpdate", worker);
        return "worker/edit/edit_worker";
    }

    // Submit update worker
    @PostMapping("/worker/update/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid Worker worker,@AuthenticationPrincipal WorkerDetails workerDetails, BindingResult result, Model model) {
        if (result.hasErrors()) {
            worker.setId(id);
            return "worker/edit/edit_worker";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(worker.getPassword());
        worker.setPassword(encodedPassword);
        workerServices.Save(worker);
        workerDetails.setWorkerName(worker.getNamesurname());
        return "redirect:/worker/home_worker";
    }

    //Job details
    @GetMapping("/worker/jobs/jobdetails/{id}")
    public String showJobDetails(@PathVariable("id") int id, Model model) {
        Integer ID= Integer.valueOf(id);
        Job_Posting job_posting = jobService.findById(ID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        model.addAttribute("jobDetails", job_posting);
        List<Resume> listResumes = resumeService.findAll();
        model.addAttribute("listResumes", listResumes);

        return "worker/jobdetails";
    }

    //Company details
    @GetMapping("/worker/companies/companydetails/{id}")
    public String showCompanyDetails(@PathVariable("id") int id, Model model) {
        Integer ID= Integer.valueOf(id);
        Company company = companyServices.findById(ID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + id));
        model.addAttribute("companyDetails", company);
        return "worker/companydetails";
    }

    //List my resumes
    @GetMapping("/worker/my_resumes")
    public String viewWorkerListResumes(Model model) {
        List<Resume> listResumes = resumeService.findAll();
        model.addAttribute("listResumes", listResumes);

        return "worker/my_resumes";
    }

    //Add resume
    @GetMapping("/worker/my_resumes/{id}")
    public String showWorkerResumeAddForm(@PathVariable("id") int id,Model model) {
        Integer ID= Integer.valueOf(id);
        Job_Posting job_posting = jobService.findById(ID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        model.addAttribute("jobDetails", job_posting);
        model.addAttribute("new_resume", new Resume());
        return "worker/add/add_resumes";
    }

    //Submit send resume
    @PostMapping("/worker/save_resume")
    public String saveResume(@Validated Resume resume, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "worker/add/add_resumes";
        }

        resumeService.Save(resume);

        return "redirect:/worker/jobs";
    }
    //like jobpost
    @PostMapping("/worker/like_job/{idjob}/{idworker}")
    public String likeJoke(@PathVariable("idjob") int idjob,@PathVariable("idworker") int idworker,@Validated LikePosts likePosts, Model model){

        Integer IDJob= Integer.valueOf(idjob);
        Integer IDWorker= Integer.valueOf(idworker);
        Optional<LikePosts> likePosts1= likePostService.findByIdjobAndIdworker(IDJob,IDWorker);
        if(likePosts1.isPresent())
        {
            LikePosts likePosts2=likePosts1.get();
            Integer likeid= likePosts2.getId();
            model.addAttribute("likePosts2",likeid);
            likePostService.Delete(likePosts2);

        }
        else
        {
            likePostService.Save(likePosts);
        }


        return "redirect:/worker/jobs";
    }

}
