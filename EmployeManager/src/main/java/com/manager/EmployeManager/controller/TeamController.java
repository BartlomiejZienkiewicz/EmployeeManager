package com.manager.EmployeManager.controller;

import com.manager.EmployeManager.entity.Task;
import com.manager.EmployeManager.entity.Team;
import com.manager.EmployeManager.entity.Worker;
import com.manager.EmployeManager.service.TaskService;
import com.manager.EmployeManager.service.TeamService;
import com.manager.EmployeManager.service.UserService;
import com.manager.EmployeManager.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;

    @Autowired
    WorkerService workerService;

    @Autowired
    TaskService taskService;

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

    @RequestMapping("/main_page/teams")
    private ModelAndView groupPanel( ModelAndView mav ){

        Integer idOfUser = userService.getIdOfUserByHisUsername(getNameOfLoggedUserUser());

        mav.setViewName("teamspanel");
        mav.addObject("listOfTeams", teamService.getTeamsByIdOfOwner(idOfUser));
        mav.addObject("quantityOfTeams", teamService.getAll().size());
        return mav;
    }

    @GetMapping(value = "/add_team")
    private ModelAndView addGroup(ModelAndView mav){
        mav.setViewName("addingteam");
        return mav;
    }
    @RequestMapping(value = "/adding_team")
    private ModelAndView addingTeam(ModelAndView mav, @RequestParam("text") String text){

        if(text.equals("")){
            mav.setViewName("redirect:/main_page/teams");
            return mav;
        }
        Team team = new Team();
        team.setName(text);
        team.setIdOfUser(userService.getIdOfUserByHisUsername(getNameOfLoggedUserUser()));
        teamService.saveTeam(team);
        mav.setViewName("redirect:/main_page/teams");
        return mav;
    }
    @RequestMapping(value = "/team/{id}")
    private ModelAndView  showTeam(ModelAndView mav, @PathVariable("id") Integer idOfTeam){

        Integer idOfBoss = userService.getIdOfUserByHisUsername(getNameOfLoggedUserUser());

        Team team = teamService.getTeamById(idOfTeam);

        List<Worker> workerList = workerService.findByIdOfTeam(idOfTeam);
        List<Worker> listOfWorkersFromAnotherTeams = workerService.findWorkersFromOtherTeams(idOfTeam,
                idOfBoss);


        List<Task> taskList = taskService.findActiveTasksByIdOfTeam(idOfTeam);


        mav.addObject("taskList", taskList);
        mav.addObject("workers", workerList);
        mav.addObject("team", team);
        mav.addObject("workersFromOtherTeams", listOfWorkersFromAnotherTeams);
        mav.setViewName("team");
        return mav;
    }
    @RequestMapping(value= "/delete_worker_from_team/{idOfTeam}/{idOfWorker}")
    private ModelAndView deleteWorker(ModelAndView mav, @PathVariable("idOfTeam") Integer idOfTeam,
                                    @PathVariable("idOfWorker") Integer idOfWorker){


        workerService.giveWorkerNewTeamById(idOfWorker,0);
        mav.setViewName("redirect:/team/"+idOfTeam);
        return mav;
    }
    @RequestMapping(value = "/edit_worker/{idOfTeam}/{idOfWorker}")
    private ModelAndView editWorker(ModelAndView mav, @PathVariable("idOfWorker")String idOfWorker,
                                   @PathVariable("idOfTeam") String idOfTeam){
        mav.setViewName("editingworker");
        Integer intIdOfWorker = Integer.parseInt(idOfWorker);
        mav.addObject("idOfTeam",Integer.parseInt(idOfTeam));
        mav.addObject("worker",workerService.findById(intIdOfWorker));
        return mav;
    }
    @GetMapping(value = "/change_worker/{idOfTeam}/{idOfWorker}")
    private ModelAndView changeWorker(ModelAndView mav, @PathVariable("idOfWorker") String idOfWorker,
                                     @PathVariable("idOfTeam") String idOfTeam,
                                     @RequestParam("name") String name, @RequestParam("lastName") String lastName,
                                     @RequestParam("jobTitle") String jobTitle){

        Integer intIdOfWorker = Integer.parseInt(idOfWorker);
        //zmieniłem metodę poniżej
        workerService.updateWorkerAndGiveNewId(intIdOfWorker,name,lastName,jobTitle);
        mav.setViewName("redirect:/team/"+idOfTeam);
        return mav;
    }
    @GetMapping(value = "/assign_worker_to_team/{teamId}/{workerId}")
    private ModelAndView assignWorker(ModelAndView mav,
                                        @PathVariable("teamId") Integer teamId,
                                        @PathVariable("workerId") Integer workerId){
        workerService.giveWorkerNewTeamById(workerId,teamId);
        mav.setViewName("redirect:/team/"+teamId);
        return mav;
    }
    @GetMapping(value = "/add_task/{teamId}")
    private ModelAndView addTask(ModelAndView mav,
                                 @PathVariable("teamId") Integer teamId,
                                 @RequestParam("name") String taskName,
                                 @RequestParam("description") String taskDescription){

        if(taskName.length()==0 || taskDescription.length()==0){
            mav.setViewName("redirect:/team/"+teamId);
            return mav;
        }

        taskService.saveTask(taskName,taskDescription,teamId);

        mav.setViewName("redirect:/team/"+teamId);
        return mav;
    }
    @GetMapping(value = "/delete_task/{taskId}/{teamId}")
    private ModelAndView deleteTask(ModelAndView mav,
                                    @PathVariable("taskId") Integer taskId,
                                    @PathVariable("teamId") Integer teamId){
        taskService.deleteTaskById(taskId);
        mav.setViewName("redirect:/team/"+teamId);
        return mav;
    }
    @GetMapping(value = "/set_as_unfinished/{taskId}/{teamId}")
    private ModelAndView setAsUnfinished(ModelAndView mav,
                                         @PathVariable("taskId") Integer taskId,
                                         @PathVariable("teamId") Integer teamId){
        taskService.setTaskAsUnfinished(taskId);

        mav.setViewName("redirect:/team/"+teamId);
        return mav;

    }
    @GetMapping(value = "/set_as_finished/{taskId}/{teamId}")
    private ModelAndView setAsFinished(ModelAndView mav,
                                         @PathVariable("taskId") Integer taskId,
                                         @PathVariable("teamId") Integer teamId){
        taskService.setTaskAsFinished(taskId);

        mav.setViewName("redirect:/team/"+teamId);
        return mav;

    }
    @RequestMapping(value = "/historyOfTeam/{teamId}")
    public ModelAndView showHistoryOfTeam(ModelAndView mav,
                                          @PathVariable("teamId") Integer teamId){

        List<Task> listOfInactiveTasks = new ArrayList<>();
        List<Task> allInactiveTasks = taskService.findInactiveTasks(teamId);

        for (int i = allInactiveTasks.size()-1; i >= 0; i--){
            listOfInactiveTasks.add(allInactiveTasks.get(i));
        }

        mav.addObject("listOfTasks", listOfInactiveTasks);
        mav.setViewName("teamhistory");
        return mav;
    }
}
