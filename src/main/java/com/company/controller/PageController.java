package com.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Контроллер, управляющий веб-страницами
 */
@Controller
public class PageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showIndexPage() {
        return "index";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String showAdminPage() {
        return "admin/admin";
    }

    @RequestMapping(value = "/admin/create_acc", method = RequestMethod.GET)
    public String showAdminCreateAccountPage() {
        return "admin/createAccount";
    }

    @RequestMapping(value = "/admin/all_accs", method = RequestMethod.GET)
    public String showAdminAllAccountsPage() {
        return "admin/allAccounts";
    }

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String showManagerPage() {
        return "manager/manager";
    }

    @RequestMapping(value = "/manager/create", method = RequestMethod.GET)
    public String showManagerCreateProjectPage() {
        return "manager/create";
    }

    @RequestMapping(value = "/manager/info/{projectId}", method = RequestMethod.GET)
    public String showManagerProjectInfoPage(@PathVariable int projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "manager/info";
    }

    @RequestMapping(value = "/manager/info", method = RequestMethod.GET)
    public String showManagerProjectInfoPage(Model model) {
        return showManagerProjectInfoPage(0, model);
    }

    @RequestMapping(value = "/manager/devs/{projectId}", method = RequestMethod.GET)
    public String showManagerProjectDevelopersPage(@PathVariable int projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "manager/devs";
    }

    @RequestMapping(value = "/manager/devs", method = RequestMethod.GET)
    public String showManagerProjectDevelopersPage(Model model) {
        return showManagerProjectDevelopersPage(0, model);
    }

    @RequestMapping(value = "/manager/all_projects", method = RequestMethod.GET)
    public String showManagerAllProjectsPage() {
        return "manager/allProjects";
    }

    @RequestMapping(value = "/developer", method = RequestMethod.GET)
    public String showDeveloperPage() {
        return "developer/developer";
    }

    @RequestMapping(value = "/developer/info", method = RequestMethod.GET)
    public String showDeveloperProjectInfoPage() {
        return "developer/info";
    }

    @RequestMapping(value = "/developer/chat", method = RequestMethod.GET)
    public String showDeveloperProjectChatPage() {
        return "developer/chat";
    }

    @RequestMapping(value = "/developer/all_projects", method = RequestMethod.GET)
    public String showDeveloperAllProjectsPage() {
        return "developer/allProjects";
    }
}
