package com.company.controller.rest;

import com.company.dao.ProjectDAOImpl;
import com.company.domain.ProjectDomain;
import com.company.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    @Autowired
    @Qualifier("projectDAO")
    ProjectDAOImpl projectDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getCurrentProject() {
        //@todo реализовать, когда появится аутентификация
        return "get current project";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProjectDomain> getProject(@PathVariable int id) {
        Project project = projectDAO.read(id);
        return new ResponseEntity(new ProjectDomain(project), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectDomain>> getAllProjects() {
        List<ProjectDomain> projectDomains = new ArrayList<>();
        for (Project project : projectDAO.readAll()) {
            projectDomains.add(new ProjectDomain(project));
        }
        return new ResponseEntity(projectDomains, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createProject(@RequestBody ProjectDomain projectDomain) {
        projectDAO.create(projectDomain);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable int id) {
        projectDAO.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/current/{id}", method = RequestMethod.POST)
    public String addDeveloperToCurrentProject(@PathVariable int id) {
        //@todo реализовать, когда появится аутентификация
        return "add developer " + id + " to current project";
    }

    @RequestMapping(value = "/current/{id}", method = RequestMethod.DELETE)
    public String removeDeveloperFromCurrentProject(@PathVariable int id) {
        //@todo реализовать, когда появится аутентификация
        return "remove developer " + id + " from current project";
    }

    @RequestMapping(value = "/current/active", method = RequestMethod.PUT)
    public String setCurrentProjectActive() {
        //@todo реализовать, когда появится аутентификация
        return "set current project active";
    }

    @RequestMapping(value = "/current/complete", method = RequestMethod.PUT)
    public String setCurrentProjectComplete() {
        //@todo реализовать, когда появится аутентификация
        return "set current project complete";
    }
}
