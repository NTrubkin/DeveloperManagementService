package com.company.controller.rest;

import com.company.dao.AccountDAOImpl;
import com.company.dao.ProjectDAOImpl;
import com.company.domain.ProjectDomain;
import com.company.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

    @Autowired
    @Qualifier("projectDAO")
    ProjectDAOImpl projectDAO;

    @Autowired
    @Qualifier("accountDAO")
    AccountDAOImpl accountDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ProjectDomain> getCurrentProject(Authentication authentication) {
        //@todo реализовать, когда появится аутентификация
        String auth = authentication.getName();
        int authId = accountDAO.read(auth).getId();
        Project project = projectDAO.getCurrentProject(authId);
        return new ResponseEntity<>(new ProjectDomain(project), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProjectDomain> getProject(@PathVariable int id) {
        Project project = projectDAO.read(id);
        return new ResponseEntity<>(new ProjectDomain(project), HttpStatus.OK);
    }

/*    //unsafe
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectDomain>> getAllProjects() {
        List<ProjectDomain> projectDomains = new ArrayList<>();
        for (Project project : projectDAO.readAll()) {
            projectDomains.add(new ProjectDomain(project));
        }
        return new ResponseEntity<>(projectDomains, HttpStatus.OK);
    }*/

    @RequestMapping(value = "/all_my", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectDomain>> getAllMyProjects(Authentication authentication) {
        String auth = authentication.getName();
        int authId = accountDAO.read(auth).getId();
        List<ProjectDomain> projectDomains = new ArrayList<>();
        for (Project project : projectDAO.readAll(authId)) {
            projectDomains.add(new ProjectDomain(project));
        }
        return new ResponseEntity<>(projectDomains, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createProject(@RequestBody ProjectDomain projectDomain, Authentication authentication) {
        String auth = authentication.getName();
        projectDomain.setManagerId(accountDAO.read(auth).getId());
        projectDAO.create(projectDomain);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable int id) {
        // @todo проверка на авторство
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

    @RequestMapping(value = "/{id}/active", method = RequestMethod.PUT)
    public String setCurrentProjectActive() {
        //@todo реализовать, когда появится аутентификация
        return "set current project active";
    }

    @RequestMapping(value = "/current/complete", method = RequestMethod.PUT)
    public ResponseEntity setCurrentProjectComplete(Authentication authentication) {
        //@todo реализовать, когда появится аутентификация
        String auth = authentication.getName();
        int authId = accountDAO.read(auth).getId();
        Project project = projectDAO.getCurrentProject(authId);
        if (project == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        else {
            project.setComplete(true);
            projectDAO.update(project);
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
