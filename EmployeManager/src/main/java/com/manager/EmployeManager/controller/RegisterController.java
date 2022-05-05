package com.manager.EmployeManager.controller;

import com.manager.EmployeManager.component.mailer.SignUpMailer;
import com.manager.EmployeManager.entity.User;
import com.manager.EmployeManager.repository.UserRepository;
import com.manager.EmployeManager.service.SignUpService;
import com.manager.EmployeManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Objects;

@Controller
public class RegisterController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserService userService;

    @Autowired
    private SignUpMailer signUpMailer;



    @GetMapping(value = "/sign_up")
    public ModelAndView signUp(ModelAndView mav){
        mav.setViewName("signup");
        mav.addObject("user",new User());
        return mav;

    }

    @GetMapping(value = "/sign_up_bad_input")
    public ModelAndView signUp2(ModelAndView mav){
        mav.setViewName("signup_bad_input");
        mav.addObject("user",new User());
        return mav;

    }

    @GetMapping(value = "/sign_up_username_is_taken")
    public ModelAndView signUp3(ModelAndView mav){
        mav.setViewName("signup_username_is_taken");
        mav.addObject("user",new User());

        return mav;

    }

    @PostMapping(value = "/sign_up")
    public ModelAndView signUpPost(ModelAndView mav, @RequestParam("username") String username,
                                   @RequestParam("password") String password, @RequestParam("email") String email)  {


        if((Objects.equals(password, "")) || (Objects.equals(username, "")) || (Objects.equals(email, ""))){

            ModelAndView mav2 = new ModelAndView();
            mav2.setViewName("redirect:/sign_up_bad_input");
            return mav2;
        }



        List<User> listOfAllUsers = userService.getUsers();
        for( User user : listOfAllUsers){
            String usernameOfUser = user.getUsername();
            if(username.equals(usernameOfUser)){
                ModelAndView mav2 = new ModelAndView();
                mav2.setViewName("/sign_up_username_is_taken");
                mav2.addObject("username2", username);
                return mav2;
            }
        }
        User userToSignUp = new User(username, password, "USER" ,email);

        signUpService.signUpUser(userToSignUp);

        mav.setViewName("redirect:/email_confirmation");
        mav.addObject("email",email);
        return mav;

    }

    @RequestMapping("/email_confirmation")
    public ModelAndView emailConfirmation(ModelAndView mav ,@RequestParam("email") String email){
        mav.setViewName("email_confirmation");
        mav.addObject("email",email);

        return mav;
    }

    @RequestMapping("/confirm_email")
    public ModelAndView confirmEmail(ModelAndView mav,@RequestParam(name="token") String tokenOfSigningUser){

        User userFoundByConfirmationToken =
                userService.getUserByConfirmationToken(tokenOfSigningUser);
        

        userService.getUserByConfirmationToken(tokenOfSigningUser).setEnabled(true);

        userService.updateUser(userFoundByConfirmationToken);
        mav.setViewName("redirect:/email_confirmed");
        return mav;

    }
    @RequestMapping("/email_confirmed")
    public ModelAndView emailConfirmed(ModelAndView mav){
        mav.setViewName("accountconfirmed.html");
        return mav;
    }





}
