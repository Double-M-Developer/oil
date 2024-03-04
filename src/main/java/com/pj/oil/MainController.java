package com.pj.oil;

import com.pj.oil.gasStation.GasStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
public class MainController {

    @RequestMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/map")
    public String map() {
        return "sidoMap";
    }

}
