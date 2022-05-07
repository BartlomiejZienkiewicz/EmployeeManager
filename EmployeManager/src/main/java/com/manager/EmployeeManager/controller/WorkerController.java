package com.manager.EmployeeManager.controller;

import com.manager.EmployeeManager.entity.Worker;
import com.manager.EmployeeManager.service.UserService;
import com.manager.EmployeeManager.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WorkerController {


    @Autowired
    WorkerService workerService;

    @Autowired
    UserService userService;


    private String getNameOfLoggedUserUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = new String();

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        String finalUsername = username;

        return finalUsername;

    }

    @RequestMapping(value = "/workers")
    private ModelAndView workers(ModelAndView mav){

        mav.addObject("workers",workerService.getWorkersThatWorkForUser(getNameOfLoggedUserUser()));
        mav.setViewName("workers");
        return mav;
    }
    @RequestMapping(value = "/adding_worker")
    private ModelAndView addingWorker(ModelAndView mav, @RequestParam("name") String name,
                                      @RequestParam("lastName") String lastName,
                                      @RequestParam("jobTitle") String jobTitle){
        Worker worker = new Worker();
        worker.setName(name);
        worker.setLastName(lastName);
        worker.setJobTitle(jobTitle);
        worker.setIdOfBoss(userService.getIdOfUserByHisUsername(getNameOfLoggedUserUser()));
        workerService.saveWorker(worker);


        mav.setViewName("redirect:/workers");
        return mav;
    }

    @RequestMapping(value = "/delete_worker/{id}")
    private ModelAndView deleteWorker(ModelAndView mav, @PathVariable("id") String id){
        Integer intId = Integer.parseInt(id);
        workerService.deleteWorkerById(intId);

        mav.setViewName("redirect:/workers");
        return mav;
    }
    @RequestMapping(value = "/worker_details/{id}")
    private ModelAndView workerDetails(ModelAndView mav, @PathVariable("id") String id){

        Integer intId = Integer.parseInt(id);


        Worker worker = workerService.findById(intId);


        mav.addObject("worker",worker);
        mav.setViewName("worker");
        return mav;
    }
    @RequestMapping(value = "/edit_worker/{id}")
    private ModelAndView editWorker(ModelAndView mav, @PathVariable("id") Integer id,
                                    @RequestParam("name") String name,
                                    @RequestParam("lastName") String lastName,
                                    @RequestParam("jobTitle") String jobTitle){


        Integer newId = workerService.updateWorkerAndGiveNewId(id,name,lastName,jobTitle) + 1;



        mav.setViewName("redirect:/worker_details/"+newId);
        return mav;
    }

}
