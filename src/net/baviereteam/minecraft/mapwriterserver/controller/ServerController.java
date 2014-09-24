package net.baviereteam.minecraft.mapwriterserver.controller;

import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class ServerController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

}