package com.manager.EmployeManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainPageController {


    @RequestMapping("/main_page")
    public ModelAndView mainPage(ModelAndView mav){
        mav.setViewName("mainpage");
        return mav;
    }

}
