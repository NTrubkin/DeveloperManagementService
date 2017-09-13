package com.company.controller.rest;

import com.company.dao.api.AccountDAO;
import com.company.dao.api.CommentaryDAO;
import com.company.dao.api.DeveloperDAO;
import com.company.dao.api.ProjectDAO;
import com.company.domain.AccountDomain;
import com.company.domain.CommentaryDomain;
import com.company.domain.ProjectDomain;
import com.company.entity.Account;
import com.company.entity.Commentary;
import com.company.entity.Developer;
import com.company.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Контроллер, управляющий частью Rest-сервиса.
 * Здесь представлены методы управления проектами
 */
@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    private static final String ROLE_DEV_CODE = "ROLE_DEV";
    private static final String ROLE_MANAGER_CODE = "ROLE_MANAGER";

    private static final String BAD_REQ = "Bad Request: ";
    private static final String FORBID = "Forbidden: ";


    @Autowired
    @Qualifier("projectDAO")
    ProjectDAO projectDAO;

    @Autowired
    @Qualifier("accountDAO")
    AccountDAO accountDAO;

    @Autowired
    @Qualifier("developerDAO")
    DeveloperDAO developerDAO;

    @Autowired
    @Qualifier("commentaryDAO")
    CommentaryDAO commentaryDAO;

    /**
     * Возвращает информацию о текущих проектах аутентифицированного пользователя
     * Метод доступен менеджерам и разработчикам
     * Разработчики получают коллекцию из одного или ни одного проекта(ограничение на кол-во активных проектов)
     *
     * @param authentication
     * @return ProjectDomain и HttpStatus.OK, если успех
     * если текущего активного проекта не существует, пустой List
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectDomain>> getCurrentProject(Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        int authId = account.getId();

        List<ProjectDomain> projectDomains = new ArrayList<>();
        switch (account.getRole().getCode()) {
            case ROLE_MANAGER_CODE: {
                List<Project> projects = projectDAO.getCurrentManagerProjects(authId);
                for(Project project : projects) {
                    projectDomains.add(new ProjectDomain(project));
                }
                return new ResponseEntity<>(projectDomains, HttpStatus.OK);
            }
            case ROLE_DEV_CODE: {
                Project project = projectDAO.getCurrentDeveloperProject(authId);
                if(project != null) {
                    projectDomains.add(new ProjectDomain(project));
                }
                return new ResponseEntity<>(projectDomains, HttpStatus.OK);
            }
            default:
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Возвращает информацию о проекте с id
     *
     * @param projectId
     * @return ProjectDomain и HttpStatus.OK, если успех,
     * если проекта с id projectId не существует, HttpStatus.BAD_REQUEST,
     * если аутентифицированный пользователь не является менедрером проекта с id projectId, HttpStatus.FORBIDDEN
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<ProjectDomain> getProject(@PathVariable int projectId, Authentication authentication) {
        Project project = projectDAO.read(projectId);
        if (project == null) {
            return new ResponseEntity(BAD_REQ + "Project with id " + projectId + " doesn't exists", HttpStatus.BAD_REQUEST);
        }
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        if (!Objects.equals(project.getManager().getId(), account.getId())) {
            return new ResponseEntity(FORBID + "You are not manager of project with id " + projectId, HttpStatus.FORBIDDEN);
        }

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
            case ROLE_MANAGER_CODE:
                for (Project project : projectDAO.readAllManagerProjects(authId)) {
                    projectDomains.add(new ProjectDomain(project));
                }
                return new ResponseEntity<>(projectDomains, HttpStatus.OK);
            case ROLE_DEV_CODE:
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
     * @return HttpStatus.OK, если успех,
     * если estimated end уже прошел, HttpStatus.BAD_REQUEST
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createProject(@RequestBody ProjectDomain projectDomain, Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        projectDomain.setManagerId(account.getId());
        projectDomain.setStart(System.currentTimeMillis());
        if(projectDomain.getEstimatedEnd() < System.currentTimeMillis()) {
            return new ResponseEntity("You are trying to create project with estimated end in the past", HttpStatus.BAD_REQUEST);
        }
        projectDomain.setComplete(false);
        projectDomain.setEnd(null);
        projectDAO.create(projectDomain);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Удаляет проект с id из системы
     *
     * @param projectId
     * @return HttpStatus.OK, если успех,
     * если проекта с id projectId не существует, HttpStatus.BAD_REQUEST
     * если аутентифицированный аккаунт не является менеджером проекта, HttpStatus.FORBIDDEN
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable int projectId, Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        Project project = projectDAO.read(projectId);
        if (project == null) {
            return new ResponseEntity(BAD_REQ + "Project with id " + projectId + " doesn't exists", HttpStatus.BAD_REQUEST);
        }
        if (!account.getId().equals(project.getManager().getId())) {
            return new ResponseEntity(FORBID + "You are trying to delete not your project", HttpStatus.FORBIDDEN);
        }
        projectDAO.delete(projectId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Отмечает проект с id активным
     * Важно помнить, что при восстановлении проекта все разработчики исключаются из проекта.
     * Это необходимо, чтобы избежать конфликта текущих проектов,
     * поскольку каждый разработчик может иметь только один активный проект
     *
     * @param projectId
     * @return HttpStatus.OK, если успех,
     * если проект с id projectId не найден HttpStatus.BAD_REQUEST,
     * если аутентифицированный аккаунт не является менеджером проекта, HttpStatus.FORBIDDEN
     */
    @RequestMapping(value = "/{projectId}/active", method = RequestMethod.PUT)
    public ResponseEntity setCurrentProjectActive(@PathVariable int projectId, Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        Project project = projectDAO.read(projectId);
        if (project == null) {
            return new ResponseEntity(BAD_REQ + "Project with id " + projectId + " doesn't exists", HttpStatus.BAD_REQUEST);
        }
        if (!account.getId().equals(project.getManager().getId())) {
            return new ResponseEntity(FORBID + "You are trying to set active not your project", HttpStatus.FORBIDDEN);
        }

        developerDAO.deleteAllProjectDevelopers(projectId);
        project.setComplete(false);
        project.setEnd(null);
        projectDAO.update(project);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Отмечает проект с id projectId выполненным
     *
     * @param authentication
     * @return HttpStatus.OK, если успех,
     * если проект с id projectId не найден HttpStatus.BAD_REQUEST,
     * если аутентифицированный аккаунт не является менеджером проекта, HttpStatus.FORBIDDEN
     */
    @RequestMapping(value = "/{projectId}/complete", method = RequestMethod.PUT)
    public ResponseEntity setCurrentProjectComplete(@PathVariable int projectId, Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        Project project = projectDAO.read(projectId);
        if (project == null) {
            return new ResponseEntity(BAD_REQ + "Project with id " + projectId + " is not exists", HttpStatus.BAD_REQUEST);
        }
        if(!account.getId().equals(project.getManager().getId())) {
            return new ResponseEntity(FORBID + "You are trying to set complete not your project", HttpStatus.FORBIDDEN);
        }
        project.setComplete(true);
        project.setEnd(new Timestamp(System.currentTimeMillis()));
        projectDAO.update(project);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Назначает разработчика с developerId в проект с projectId
     *
     * @param projectId
     * @param developerId
     * @return HttpStatus.OK, если успех,
     * если проекта с id projectId не существует, HttpStatus.BAD_REQUEST,
     * если аутентифицированный аккаунт не является менеджером проекта, HttpStatus.FORBIDDEN,
     * если разработчик с id developerId не существует, HttpStatus.BAD_REQUEST,
     * если аккаунт с id developerId не является разработчиком, HttpStatus.BAD_REQUEST,
     * если разработчик уже имеет текущий проект, HttpStatus.BAD_REQUEST,
     */
    @RequestMapping(value = "/{projectId}/dev/{developerId}", method = RequestMethod.POST)
    public ResponseEntity addDeveloperToCurrentProject(@PathVariable int projectId, @PathVariable int developerId, Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        Project project = projectDAO.read(projectId);
        if (project == null) {
            return new ResponseEntity(BAD_REQ + "Project with id " + projectId + " doesn't exists", HttpStatus.BAD_REQUEST);
        }
        if (!account.getId().equals(project.getManager().getId())) {
            return new ResponseEntity(FORBID + "You are trying to set developer to not your project", HttpStatus.FORBIDDEN);
        }
        Account developerAccount = accountDAO.read(developerId);
        if (developerAccount == null) {
            return new ResponseEntity(BAD_REQ + "Developer with id " + developerId + " doesn't exists", HttpStatus.BAD_REQUEST);
        }
        if (!developerAccount.getRole().getCode().equals(ROLE_DEV_CODE)) {
            return new ResponseEntity(BAD_REQ + "Account with id " + developerId + " is not developer", HttpStatus.BAD_REQUEST);
        }
        Project developerCurrentProject = projectDAO.getCurrentDeveloperProject(developerId);
        if (developerCurrentProject != null) {
            return new ResponseEntity(BAD_REQ + "Developer with id " + developerId + " already has current project", HttpStatus.BAD_REQUEST);
        }

        Developer developer = new Developer(developerAccount, project);
        developerDAO.create(developer);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Удаляет разработчика с developerId из проекта с projectId
     *
     * @param projectId
     * @param developerId
     * @return HttpStatus.OK, если успех,
     * если проект с id projectId не существует, HttpStatus.BAD_REQUEST,
     * если аутентифицированный аккаунт не является менеджером проекта, HttpStatus.FORBIDDEN,
     * если разработчик с id developerId не существует, HttpStatus.BAD_REQUEST,
     * если факта привязки разработчика, к проекту не существует, HttpStatus.BAD_REQUEST,
     */
    @RequestMapping(value = "/{projectId}/dev/{developerId}", method = RequestMethod.DELETE)
    public ResponseEntity removeDeveloperFromCurrentProject(@PathVariable int projectId, @PathVariable int developerId, Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        Project project = projectDAO.read(projectId);
        if (project == null) {
            return new ResponseEntity(BAD_REQ + "Project with id " + projectId + " doesn't exists", HttpStatus.BAD_REQUEST);
        }
        if (!account.getId().equals(project.getManager().getId())) {
            return new ResponseEntity(FORBID + "You are trying to delete developer from not your project", HttpStatus.FORBIDDEN);
        }
        Account developerAccount = accountDAO.read(developerId);
        if (developerAccount == null) {
            return new ResponseEntity(BAD_REQ + "Developer with id " + developerId + " doesn't exists", HttpStatus.BAD_REQUEST);
        }
        try {
            developerDAO.delete(projectId, developerId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (IllegalArgumentException exc) {
            return new ResponseEntity(BAD_REQ + "Developer with id " + developerId + " at project with id " + projectId + " not found", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Возвращает всех разработчиков проекта с id
     *
     * @param projectId
     * @return List<AccountDomain> и HttpStatus.OK, если успех,
     * если проект с id projectId не существует, HttpStatus.BAD_REQUEST,
     * если аутентифицированный аккаунт не является менеджером проекта, HttpStatus.FORBIDDEN,
     */
    @RequestMapping(value = "/{projectId}/dev/all", method = RequestMethod.GET)
    public ResponseEntity<List<AccountDomain>> getDevelopersInProject(@PathVariable int projectId, Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        Project project = projectDAO.read(projectId);
        if (project == null) {
            return new ResponseEntity(BAD_REQ + "Project with id " + projectId + " doesn't exists", HttpStatus.BAD_REQUEST);
        }
        if (!account.getId().equals(project.getManager().getId())) {
            return new ResponseEntity(FORBID + "You are trying to get all developers from not your project", HttpStatus.FORBIDDEN);
        }

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

    @RequestMapping(value = "/{projectId}/comment/all", method = RequestMethod.GET)
    public ResponseEntity<List<CommentaryDomain>> getCommentaries(@PathVariable int projectId, Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        Project project = projectDAO.read(projectId);

        if (!project.getManager().getId().equals(account.getId()) && !developerDAO.isDeveloperOfProject(account.getId(), projectId)) {
            return new ResponseEntity(BAD_REQ + "You are not join to project with id " + projectId, HttpStatus.FORBIDDEN);
        }

        List<CommentaryDomain> commentaries = new ArrayList<>();
        for (Commentary commentary : commentaryDAO.readAll(projectId)) {
            commentaries.add(new CommentaryDomain(commentary));
        }
        return new ResponseEntity<>(commentaries, HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}/comment", method = RequestMethod.POST)
    public ResponseEntity postCommentary(@PathVariable int projectId, @RequestBody CommentaryDomain commentaryDomain, Authentication authentication) {
        String auth = authentication.getName();
        Account account = accountDAO.read(auth);
        Project project = projectDAO.read(projectId);

        if (!project.getManager().getId().equals(account.getId()) && !developerDAO.isDeveloperOfProject(account.getId(), projectId)) {
            return new ResponseEntity(BAD_REQ + "You are not join to project with id " + projectId, HttpStatus.FORBIDDEN);
        }

        commentaryDomain.setTime(System.currentTimeMillis());

        Commentary commentary = new Commentary(commentaryDomain, account, project);
        commentaryDAO.create(commentary);
        return new ResponseEntity(HttpStatus.OK);
    }
}
