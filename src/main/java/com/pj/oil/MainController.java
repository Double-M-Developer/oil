package com.pj.oil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {

    @RequestMapping("/")
    public String main() {
        return "index";
    }

    @RequestMapping("/map")
    public String map() {
        return "sidoMap";
    }
    @GetMapping("/home")
    public String home(){
        return "dashboard";
    }


}
