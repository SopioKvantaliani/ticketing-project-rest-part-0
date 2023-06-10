package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //if we have only @Controller annotation we need to return view
@RequestMapping("/api/v1/project")
public class ProjectController {

    public final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getProjects (){
       List <ProjectDTO> projectDTOList =  projectService.listAllProjects();
       return ResponseEntity.ok (new ResponseWrapper
               ("Projects successfully retrieved", projectDTOList, HttpStatus.OK));
    }
    @GetMapping("/{code}")
    public ResponseEntity <ResponseWrapper> getProjectByCode (@PathVariable("code") String code){
        ProjectDTO projectDTO= projectService.getByProjectCode(code);
        return ResponseEntity.ok (new ResponseWrapper
                ("Project successfully retrieved", projectDTO, HttpStatus.OK));

    }
    @PostMapping
    public ResponseEntity <ResponseWrapper> createProject (@RequestBody ProjectDTO project){
       projectService.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project is successfully created", HttpStatus.CREATED ));
    }
    @PutMapping
    public ResponseEntity <ResponseWrapper> updateProject (@RequestBody ProjectDTO project){
        projectService.update(project);
        return ResponseEntity.ok (new ResponseWrapper
                ("Project successfully updated", project, HttpStatus.OK));
    }
    @DeleteMapping("/{projectCode}")
    public ResponseEntity <ResponseWrapper> deleteProject (@PathVariable("projectCode") String projectCode){
        projectService.delete(projectCode);
        return ResponseEntity.ok (new ResponseWrapper
                ("Project successfully deleted", projectCode, HttpStatus.OK));
    }
    @GetMapping("/manager/project-status")
    public ResponseEntity <ResponseWrapper> getProjectByManager (){
       List<ProjectDTO> projectDTOList = projectService.listAllProjectDetails();
        return ResponseEntity.ok (new ResponseWrapper
                ("Projects successfully retrieved by manager", projectDTOList, HttpStatus.OK));
    }
    @PutMapping("/manager/complete/{projectCode}")
    public ResponseEntity <ResponseWrapper> managerCompleteProject (@PathVariable("projectCode") String projectCode){
        projectService.complete(projectCode);
        return ResponseEntity.ok (new ResponseWrapper
                ("Complete Project successfully completed", projectCode, HttpStatus.OK));
    }

}
