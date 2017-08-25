package com.company.controller.rest;

import com.company.dao.AccountDAOImpl;
import com.company.dao.DeveloperDaoImpl;
import com.company.dao.ProjectDAOImpl;
import com.company.domain.AccountDomain;
import com.company.domain.ProjectDomain;
import com.company.entity.Account;
import com.company.entity.Developer;
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

    @Autowired
    @Qualifier("developerDAO")
    DeveloperDaoImpl developerDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ProjectDomain> getCurrentProject(Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        int authId = account.getId();

        switch (account.getRole().getCode()) {
            case "ROLE_MANAGER": {
                Project project = projectDAO.getCurrentManagerProject(authId);
                return new ResponseEntity<>(new ProjectDomain(project), HttpStatus.OK);
            }
            case "ROLE_DEV": {
                Project project = projectDAO.getCurrentDeveloperProject(authId);
                return new ResponseEntity<>(new ProjectDomain(project), HttpStatus.OK);
            }
            default:
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<ProjectDomain> getProject(@PathVariable int projectId) {
        Project project = projectDAO.read(projectId);
        return new ResponseEntity<>(new ProjectDomain(project), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectDomain>> getAllProjects() {
        List<ProjectDomain> projectDomains = new ArrayList<>();
        for (Project project : projectDAO.readAll()) {
            projectDomains.add(new ProjectDomain(project));
        }
        return new ResponseEntity<>(projectDomains, HttpStatus.OK);
    }

    @RequestMapping(value = "/all_my", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectDomain>> getAllMyProjects(Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        int authId = account.getId();
        List<ProjectDomain> projectDomains = new ArrayList<>();

        switch (account.getRole().getCode()) {
            case "ROLE_MANAGER":
                for (Project project : projectDAO.readAllManagerProjects(authId)) {
                    projectDomains.add(new ProjectDomain(project));
                }
                return new ResponseEntity<>(projectDomains, HttpStatus.OK);
            case "ROLE_DEV":
                for (Project project : projectDAO.readAllDeveloperProjects(authId)) {
                    projectDomains.add(new ProjectDomain(project));
                }
                return new ResponseEntity<>(projectDomains, HttpStatus.OK);
            default:
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createProject(@RequestBody ProjectDomain projectDomain, Authentication authentication) {
        String auth = authentication.getName();
        projectDomain.setManagerId(accountDAO.read(auth).getId());
        projectDAO.create(projectDomain);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable int projectId) {
        projectDAO.delete(projectId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}/active", method = RequestMethod.PUT)
    public ResponseEntity setCurrentProjectActive(@PathVariable int projectId) {
        Project project = projectDAO.read(projectId);
        if (project == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        else {
            project.setComplete(false);
            projectDAO.update(project);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/current/complete", method = RequestMethod.PUT)
    public ResponseEntity setCurrentProjectComplete(Authentication authentication) {
        String auth = authentication.getName();
        int authId = accountDAO.read(auth).getId();
        Project project = projectDAO.getCurrentManagerProject(authId);
        if (project == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        else {
            project.setComplete(true);
            projectDAO.update(project);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{projectId}/dev/{developerId}", method = RequestMethod.POST)
    public ResponseEntity addDeveloperToCurrentProject(@PathVariable int projectId, @PathVariable int developerId) {
        Project project = projectDAO.read(projectId);
        if (project == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Account account = accountDAO.read(developerId);
        if(account == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Developer developer = new Developer(account, project);
        developerDAO.create(developer);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}/dev/{developerId}", method = RequestMethod.DELETE)
    public ResponseEntity removeDeveloperFromCurrentProject(@PathVariable int projectId, @PathVariable int developerId) {
        developerDAO.delete(projectId, developerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}/dev/all", method = RequestMethod.GET)
    public ResponseEntity<List<AccountDomain>> getDevelopersInProject(@PathVariable int projectId) {
        List<AccountDomain> devs = new ArrayList<>();
        for(Developer developer : developerDAO.readAll(projectId)) {
            devs.add(new AccountDomain(developer.getAccount()));
        }
        return new ResponseEntity<>(devs, HttpStatus.OK);
    }

    @RequestMapping(value = "/dev/avail", method = RequestMethod.GET)
    public ResponseEntity<List<AccountDomain>> getAvailableDevelopers() {
        List<AccountDomain> devs = new ArrayList<>();
        for(Account account : developerDAO.readAllAvailable()) {
            devs.add(new AccountDomain(account));
        }
        return new ResponseEntity<>(devs, HttpStatus.OK);
    }
}
