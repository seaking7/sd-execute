package com.uplus.sdexecute.controller;

import com.uplus.sdexecute.dto.ExecuteDto;
import com.uplus.sdexecute.service.ExecuteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/execute-service")
public class ExecuteController {

    private ExecuteService executeService;
    private Environment env;

    @Autowired
    public ExecuteController(ExecuteService executeService, Environment env) {
        this.executeService = executeService;
        this.env = env;
    }


    @GetMapping("/welcome")
    public String welcome(){
        return "execute-welcome";
    }

    @GetMapping("/")
    public String contentHome(){
        return "home";
    }



    @GetMapping("/content_view")
    public String viewContents(Model model){

        model.addAttribute("server_address", env.getProperty("eureka.instance.hostname"));
        model.addAttribute("server_port", env.getProperty("local.server.port"));

        return "content/viewContent";
    }

    @GetMapping("/{contentId}/execute")
    public ModelAndView executeContent(@PathVariable String contentId){
        ModelAndView mav= new ModelAndView("content/executeContent");

        ExecuteDto executeDto = executeService.getExecuteByContentId(contentId);

        mav.addObject("contentName", executeDto.getContentName());
        mav.addObject("url", executeDto.getUrl());

        mav.addObject("server_address", env.getProperty("eureka.instance.hostname"));
        mav.addObject("server_port", env.getProperty("local.server.port"));

        return mav;
    }

}
