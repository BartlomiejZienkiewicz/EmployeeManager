package com.manager.EmployeManager.controller;

import com.manager.EmployeManager.entity.Task;
import com.manager.EmployeManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/add_task/{id}")
    private ModelAndView addTask(ModelAndView mav, @PathVariable("id") String idOfTeam,
                                 @RequestParam("nameOfTask") String nameOfTask,
                                 @RequestParam("description") String descriptionOfTask){


        taskService.saveTask(nameOfTask,descriptionOfTask,Integer.parseInt(idOfTeam));

        mav.setViewName("redirect:/team/"+idOfTeam);
        return mav;
    }
}
