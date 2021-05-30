package com.uplus.sdexecute.controller;

import com.uplus.sdexecute.dto.ExecuteDto;
import com.uplus.sdexecute.kafka.ExecuteProducer;
import com.uplus.sdexecute.service.ExecuteService;
import com.uplus.sdexecute.vo.RequestDeleteContent;
import com.uplus.sdexecute.vo.ResponseContent;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/execute-service")
public class ExecuteController {

    private ExecuteService executeService;
    ExecuteProducer executeProducer;
    private Environment env;

    @Autowired
    public ExecuteController(ExecuteService executeService, ExecuteProducer executeProducer, Environment env) {
        this.executeService = executeService;
        this.executeProducer = executeProducer;
        this.env = env;
    }


    @GetMapping("/welcome")
    public String welcome(){
        return "execute-welcome";
    }

    @GetMapping("/")
    public String contentHome(){
        return "content/viewContent";
    }



    @GetMapping("/content_view")
    public String viewContents(Model model){

        Iterable<ExecuteDto> executeDto = executeService.getContentsAll();

        List<ResponseContent> result = new ArrayList<>();
        executeDto.forEach( v -> {
            result.add(new ModelMapper().map(v, ResponseContent.class));
        });
        model.addAttribute("contents", result);
        setEnvModel(model);


        return "content/viewContent";
    }

    @GetMapping("/{contentId}/execute")
    public ModelAndView executeContent(@PathVariable String contentId){
        ModelAndView mav= new ModelAndView("content/executeContent");

        ExecuteDto executeDto = executeService.getExecuteByContentId(contentId);

        executeProducer.send("executeLog", executeDto);

        mav.addObject("contentName", executeDto.getContentName());
        mav.addObject("url", executeDto.getUrl());

        mav.addObject("server_address", env.getProperty("eureka.instance.hostname"));
        mav.addObject("server_port", env.getProperty("local.server.port"));
        mav.addObject("server_service", env.getProperty("spring.application.name"));

        return mav;
    }

    @PostMapping("/execute/deleteContent")
    public ResponseEntity<ResponseContent> deleteExecute(@RequestBody RequestDeleteContent requestDeleteContent){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        executeService.deleteByContentId(requestDeleteContent.getContentId());

        ResponseContent responseContent = mapper.map(requestDeleteContent, ResponseContent.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseContent);

    }

    public void setEnvModel(Model model){
        model.addAttribute("server_address", env.getProperty("eureka.instance.hostname"));
        model.addAttribute("server_port", env.getProperty("local.server.port"));
        model.addAttribute("server_service", env.getProperty("spring.application.name"));
    }


}
