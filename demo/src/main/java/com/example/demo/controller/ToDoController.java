package com.example.demo.controller;

import com.example.demo.model.ToDo;
import com.example.demo.repository.ToDoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ToDoController {

    //this is a Todo controller class

//    //todo list
//    List<ToDo> toDoList = new ArrayList<>();
//    long ToDoNumber = 0;// --> we removed these two fiekds because we moved from using the in-memory data
    //to the postgresql database
    //Creating a toDorepo
    final ToDoRepository toDoRepo;

    public ToDoController(ToDoRepository toDoRepository){
        this.toDoRepo = toDoRepository;
    }

 //---------------------------------------------------------------------------------------->
//    @PostMapping("/todos")
//    public ToDo addtoToDoList(@RequestBody ToDo todo){
//        //call the constructor of todo
////        toDoList.add(new ToDo(1L, "Learn API creation", "2025-10-04", "6:00 PM", false));
//        todo.setID(ToDoNumber++);//giving it an id first and then incrementing counter
////        ToDoNumber++;//new id for next todo object
//        toDoList.add(todo); // adding to the list
//        return todo ;
//    }below is the updated post method

    @PostMapping("/todos")
    public ToDo addtoToDoList(@RequestBody ToDo toDo){
        return toDoRepo.save(toDo);//simple save method , saves this toDo into the record
    }//receives the updated ToDo object. returns the fully formed object to the client

//------------------------------------------------------------------------------------------------
    //get entire toDoList
    @GetMapping("/todos")
    public List<ToDo> getToDoList(){
        return toDoRepo.findAll();
    }
//-------------------------------------------------------------------------------------------------
    //get single specific ToDo object using the specific ID
    //here since we want a speciic todo obj we include ID in the url
    //also we need to search the existing list to get the todo task wit id
//    @GetMapping("/todos/{id}")
//    public ToDo getToDoById(@PathVariable long id){
//
//        ToDo result = null;
//        for(var toDo : toDoList){
//            if(id == toDo.getID()){
//                result  =  toDo;
//                break;
//            }
//        }
//        if(result == null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo item with the ID" + id+ "Not found.");
//        }
//        System.out.println("200");
//        return result ;
//    }

    @GetMapping("/todos/{id}")
    public ToDo getToDoById(@PathVariable long id){

        return toDoRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ITEM NOT FOUND"));
    }
//--------------------------------------------------------------------------------------------------------------------------->>
//    @DeleteMapping("/todos/{id}")
//    public void removeToDo(@PathVariable long id){
//
//        boolean isRemoved;
//        //toDoList.removeIf(getToDoById(id));
//        isRemoved = toDoList.removeIf((toDo -> toDo.getID() == id));//removes the toDo objects whose id match with the passed id
//        if(!isRemoved){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ITEM NOT FOUND");
//        }
//    }

    @DeleteMapping("/todos/{id}")
    public void removeToDo(@PathVariable long id){
        //uppdated method implementation after db addition
        //delete only if the item with that id exists
        if(toDoRepo.existsById(id)){
            toDoRepo.deleteById(id);//this line deletes the object by its id
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item doesnt exist");
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------->>

//    @PutMapping("/todos/{id}")
//    public void updateToDo(@PathVariable long id, @RequestBody ToDo updatedToDo){
//
//        boolean isFound = false;
//        //catch that todo object using the  id
//        for(var toDo : toDoList){
//            if(toDo.getID() == id){
//                //start making changes
//                isFound = true;
//                //check if the field is present or null in the updatedToDo
//                if(updatedToDo.getTaskDesc() != null){
//                    toDo.setTaskDesc(updatedToDo.getTaskDesc());
//                }
//
//                if(updatedToDo.getDueDate() != null){
//                    toDo.setDueDate(updatedToDo.getDueDate());
//                }
//
//                if(updatedToDo.getTime() != null){
//                    toDo.setTime(updatedToDo.getTime());
//                }
//
//                if(updatedToDo.getStatus() != null){
//                    toDo.setStatus(updatedToDo.getStatus());
//                }
//                break;
//            }
//        }
//
//        if(!isFound){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TO DO does not exist");
//        }
//    }

    @PatchMapping ("/todos/{id}")
    public ToDo updateToDo(@PathVariable long id, @RequestBody ToDo updatedToDo){

        //retrieve the Object/Record first
        ToDo existingToDo = toDoRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item with ID doesn't exist"));

        //Step 2: start making selective changes
        //check if the field is present or null in the updatedToDo
        if (updatedToDo.getTaskDesc() != null) {
            existingToDo.setTaskDesc(updatedToDo.getTaskDesc());
        }

        if (updatedToDo.getDueDate() != null) {
            existingToDo.setDueDate(updatedToDo.getDueDate());
        }

        if (updatedToDo.getTime() != null) {
            existingToDo.setTime(updatedToDo.getTime());
        }

        if (updatedToDo.getStatus() != null) {
            existingToDo.setStatus(updatedToDo.getStatus());
        }

        //step 3: save
        return toDoRepo.save(existingToDo);
        //Return Type	S	The method returns the object that was saved.
        // It returns type S to ensure it returns the most specific type possible
        // (e.g., if you save a SpecialToDo, it returns a SpecialToDo).
    }
}
/*The @PathVariable annotation is used to extract values from the URI (URL) template.
Role: It tells Spring to take the value found in the path variable placeholder in the URL
and inject it into the method parameter.
In Your Code: When a client sends a request to /todos/5, the value 5 is pulled from the path and
assigned to the method parameter long id.
The name of the parameter (id) matches the name in the URL pattern ({id}).*/