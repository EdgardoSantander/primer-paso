package com.firststep.primer_paso.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pasito")
public class FirstController {

    @GetMapping("/tuntun")
    public String saludo(){
        return "Hola mundo desde este FirstService";
    }
}
