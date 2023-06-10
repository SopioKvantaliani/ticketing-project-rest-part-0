package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController //if we have only @Controller annotation we need to return view
@RequestMapping("/api/v1/task")
public class TaskController {

    public final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity <ResponseWrapper> getTasks (){
        List<TaskDTO> taskDTOList = taskService.listAllTasks();
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", taskDTOList, HttpStatus.OK));
    }
    @GetMapping("/{taskId}")
    @RolesAllowed("Manager")
    public ResponseEntity <ResponseWrapper> getTaskById (@PathVariable("taskId") Long taskId){
        TaskDTO taskDTO = taskService.findById(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved", taskDTO, HttpStatus.OK));
    }
    @DeleteMapping("/{taskId}")
    @RolesAllowed("Manager")
    public ResponseEntity <ResponseWrapper> deleteTask (@PathVariable ("taskId") Long taskId ){
        taskService.delete(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully deleted", HttpStatus.OK));
    }
    @PostMapping
    @RolesAllowed("Manager")
    public ResponseEntity <ResponseWrapper> createTask (@RequestBody TaskDTO task){
        taskService.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body
                (new ResponseWrapper("Task is successfully created", task, HttpStatus.CREATED ));
    }
    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity <ResponseWrapper> updateTask (@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated", taskDTO, HttpStatus.OK));

    }
    @GetMapping("/employee/pending-tasks")
    @RolesAllowed("Employee")
    public ResponseEntity <ResponseWrapper> employeePendingTasks (){
        List<TaskDTO> taskDTOList = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", taskDTOList, HttpStatus.OK));

    }
    @PutMapping("/employee/update/")
    @RolesAllowed("Employee")
    public ResponseEntity <ResponseWrapper> employeeUpdatedTasks (@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated", task, HttpStatus.OK));

    }
    @GetMapping("/employee/archive")
    @RolesAllowed("Employee")
    public ResponseEntity <ResponseWrapper> employeeArchivedTasks (){
        List <TaskDTO> taskDTOList = taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", taskDTOList, HttpStatus.OK));

    }


}
