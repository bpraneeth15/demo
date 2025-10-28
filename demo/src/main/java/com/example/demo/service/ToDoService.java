package com.example.demo.service;

import com.example.demo.Specification.ToDoSpecification;
import com.example.demo.exception.ToDoNotFoundException;
import com.example.demo.model.ToDo;
import com.example.demo.repository.ToDoRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToDoService {


    private final ToDoRepository toDoRepo;

    public ToDoService(ToDoRepository toDoRepo){
        this.toDoRepo = toDoRepo;
    }

    //CREATE
    public ToDo createToDo(ToDo toDo){
        return toDoRepo.save(toDo);
    }

    //RETRIEVE
    public ToDo getToDo(long id) throws ToDoNotFoundException{
        return toDoRepo.findById(id).orElseThrow(() -> new ToDoNotFoundException("ToDo with ID : " + id + "doesnt Exist!"));
    }

    //RETRIEVE ALL todo list
    public List<ToDo> getToDoList(Boolean status, String dueDate, String taskDesc){


        Specification<ToDo> spec = ToDoSpecification.findByCriteria(status, dueDate, taskDesc);
        return toDoRepo.findAll(spec);
    }

    //SELECTIVE todo UPDATE
    public ToDo updateToDo(long id, ToDo updatedToDo) throws ToDoNotFoundException{

        //better to retrive it directly than simply checking if it exists by id or not
        ToDo existingToDo = toDoRepo.findById(id).orElseThrow(() -> new ToDoNotFoundException("ToDo with ID : " + id + "doesnt Exist!"));
        //orElseThrow takes in an exception supplier, the one that supplies exception,

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

        //STEP 3:  SAVE the updated
        return toDoRepo.save(existingToDo);
    }

    //REMOVE/DELETE
    public void removeToDo (long id) throws ToDoNotFoundException {
        //check if present
        if(toDoRepo.existsById(id)){
            toDoRepo.deleteById(id);
            System.out.println("Delete Successful");
        }
        else{
            throw new ToDoNotFoundException("ToDo with ID : " + id + " doesnt Exist!");
        }
    }
}
/*When you create or pass a Specification, you’re not executing anything — you’re just handing Spring Data JPA a plan (a blueprint) of what conditions should exist in the query.*/
/*findAll(spec) calls your toPredicate(...) method of the passed spec internally,
Builds those predicates (the actual filtering conditions),
Merges them into a CriteriaQuery,
Converts that to SQL, and
Executes it against the database.*/