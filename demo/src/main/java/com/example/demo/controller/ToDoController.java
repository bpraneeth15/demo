package com.example.demo.controller;

import com.example.demo.exception.ToDoNotFoundException;
import com.example.demo.model.ToDo;
import com.example.demo.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ToDoController {

    private final ToDoService toDoService;

    //Constructor injecting the service instance into the controller class
    public ToDoController(ToDoService toDoService){
        this.toDoService = toDoService;
    }

    @PostMapping("/todos")
    public ToDo addtoToDoList(@RequestBody ToDo toDo){
        return toDoService.createToDo(toDo);//simple save method , saves this toDo into the record
    }
//------------------------------------------------------------------------------------------------
    @GetMapping("/todos")
    public List<ToDo> getToDoList(@RequestParam(required = false) Boolean status,
                                  @RequestParam(required = false) String dueDate,
                                  @RequestParam(required = false) String taskDesc){
        return toDoService.getToDoList(status, dueDate, taskDesc);
    }

// -------------------------------------------------------------------------------------------------

//    @GetMapping("/todos/{id}")
//    public ToDo getToDoById(@PathVariable long id) throws ToDoNotFoundException {
//        return toDoService.getToDo(id);
//    }
//--------------------------------------------------------------------------------------------------------------------------->>

    @DeleteMapping("/todos/{id}")
    public void removeToDo(@PathVariable long id) throws ToDoNotFoundException{
        //updated method implementation after db addition
        //delete only if the item with that id exists
        toDoService.removeToDo(id);
    }

//----------------------------------------------------------------------------------------------------------------------------------->>
    @PatchMapping ("/todos/{id}")
    public ToDo updateToDo(@PathVariable long id, @RequestBody ToDo updatedToDo) throws ToDoNotFoundException {
        return toDoService.updateToDo(id, updatedToDo);
    }

    @ExceptionHandler(ToDoNotFoundException.class) //tells spring -> when you see a specific execution, run this method instead of crashing.
    @ResponseStatus(HttpStatus.NOT_FOUND) //sets the correct http error code
    public String handleTheToDoNotFound(ToDoNotFoundException e){
       return e.getMessage();
    }
}