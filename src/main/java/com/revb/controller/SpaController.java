package com.revb.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {
    // Any non-API route should return index.html (React handles routing)
    @RequestMapping(value = {"/{path:[^\\.]*}", "/**/{path:[^\\.]*}"})
    public org.springframework.core.io.Resource index() {
        return new ClassPathResource("/static/index.html");
    }
}
