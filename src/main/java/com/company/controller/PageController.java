package com.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showIndexPage() {
        return "index";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String showAdminPage() {
        return "admin";
    }

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String showManagerPage() {
        return "manager";
    }

    @RequestMapping(value = "/developer", method = RequestMethod.GET)
    public String showDeveloperPage() {
        return "developer";
    }
}
