package com.company.controller.rest;

import com.company.dao.AccountDAO;
import com.company.dao.DeveloperDAO;
import com.company.dao.ProjectDAO;
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

/**
 * Контроллер, управляющий частью Rest-сервиса.
 * Здесь представлены методы управления проектами
 */
@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    @Autowired
    @Qualifier("projectDAO")
    ProjectDAO projectDAO;

    @Autowired
    @Qualifier("accountDAO")
    AccountDAO accountDAO;

    @Autowired
    @Qualifier("developerDAO")
    DeveloperDAO developerDAO;

    /**
     * Возвращает информацию о текущем проекте аутентифицированного пользователя
     * Текущий проект - единственный активный проект (или ни одного)
     *
     * @param authentication
     * @return ProjectDomain и HttpStatus.OK, если успех
     */
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

    /**
     * Возвращает информацию о проекте с id
     *
     * @param projectId
     * @return ProjectDomain и HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<ProjectDomain> getProject(@PathVariable int projectId) {
        Project project = projectDAO.read(projectId);
        return new ResponseEntity<>(new ProjectDomain(project), HttpStatus.OK);
    }

    /**
     * Возвращает информацию о всех проектах, принадлежащих аутентифицированному пользователю
     *
     * @param authentication
     * @return List<ProjectDomain> и HttpStatus.OK, если успех
     */
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

    /**
     * Создает проект в системе от имени аутентифицированного пользователя
     *
     * @param projectDomain
     * @param authentication
     * @return HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createProject(@RequestBody ProjectDomain projectDomain, Authentication authentication) {
        String auth = authentication.getName();
        projectDomain.setManagerId(accountDAO.read(auth).getId());
        projectDAO.create(projectDomain);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Удаляет проект с id из системы
     *
     * @param projectId
     * @return HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable int projectId) {
        projectDAO.delete(projectId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Отмечает проект с id активным
     *
     * @param projectId
     * @return HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/{projectId}/active", method = RequestMethod.PUT)
    public ResponseEntity setCurrentProjectActive(@PathVariable int projectId) {
        Project project = projectDAO.read(projectId);
        if (project == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        else {
            developerDAO.deleteAllProjectDevelopers(projectId);
            project.setComplete(false);
            projectDAO.update(project);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    /**
     * Отмечает текужий проект выполненным
     *
     * @param authentication
     * @return HttpStatus.OK, если успех
     */
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

    /**
     * Назначает разработчика с developerId в проект с projectId
     *
     * @param projectId
     * @param developerId
     * @return HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/{projectId}/dev/{developerId}", method = RequestMethod.POST)
    public ResponseEntity addDeveloperToCurrentProject(@PathVariable int projectId, @PathVariable int developerId) {
        Project project = projectDAO.read(projectId);
        if (project == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Account account = accountDAO.read(developerId);
        if (account == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Developer developer = new Developer(account, project);
        developerDAO.create(developer);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Удаляет разработчика с developerId из проекта с projectId
     *
     * @param projectId
     * @param developerId
     * @return HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/{projectId}/dev/{developerId}", method = RequestMethod.DELETE)
    public ResponseEntity removeDeveloperFromCurrentProject(@PathVariable int projectId, @PathVariable int developerId) {
        developerDAO.delete(projectId, developerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Возвращает всех разработчиков проекта с id
     *
     * @param projectId
     * @return List<AccountDomain> и HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/{projectId}/dev/all", method = RequestMethod.GET)
    public ResponseEntity<List<AccountDomain>> getDevelopersInProject(@PathVariable int projectId) {
        List<AccountDomain> devs = new ArrayList<>();
        for (Developer developer : developerDAO.readAll(projectId)) {
            devs.add(new AccountDomain(developer.getAccount()));
        }
        return new ResponseEntity<>(devs, HttpStatus.OK);
    }

    /**
     * Возвращает всех разработчиков, которых можно назначить в любой проект,
     * то есть список всех свободных на данный момент разработчиков(без назначенного текущего проекта)
     *
     * @return List<AccountDomain> и HttpStatus.OK, если успех
     */
    @RequestMapping(value = "/dev/avail", method = RequestMethod.GET)
    public ResponseEntity<List<AccountDomain>> getAvailableDevelopers() {
        List<AccountDomain> devs = new ArrayList<>();
        for (Account account : developerDAO.readAllAvailable()) {
            devs.add(new AccountDomain(account));
        }
        return new ResponseEntity<>(devs, HttpStatus.OK);
    }
}
