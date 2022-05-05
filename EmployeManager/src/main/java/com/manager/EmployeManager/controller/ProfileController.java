package com.manager.EmployeManager.controller;

import com.manager.EmployeManager.entity.User;
import com.manager.EmployeManager.service.TeamService;
import com.manager.EmployeManager.service.UserService;
import com.manager.EmployeManager.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {

    @Autowired
    TeamService teamService;

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

    @RequestMapping(value = "/profile")
    private ModelAndView showAccount(ModelAndView mav){

        User user = userService.getUserByUsername(getNameOfLoggedUserUser());
        Integer numberOfTeams =
                teamService.getTeamsByIdOfOwner
                        (userService.getIdOfUserByHisUsername(getNameOfLoggedUserUser())).size();

        Integer numberOfWorkers = workerService.getWorkersThatWorkForUser(getNameOfLoggedUserUser()).size();

        mav.addObject("numberOfWorkers", numberOfWorkers);
        mav.addObject("numberOfTeams", numberOfTeams);
        mav.addObject("user",user);
        mav.setViewName("profile");
        return mav;
    }
}
