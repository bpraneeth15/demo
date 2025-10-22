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

    //since we wanted to introduce a service layer in the middle we give this the access to the repository that we are using
    //and remove the access to the repository from the controller layer
    private final ToDoRepository toDoRepo;

    public ToDoService(ToDoRepository toDoRepo){
        this.toDoRepo = toDoRepo;
    }

    //CREATE
    public ToDo createToDo(ToDo toDo){
        return toDoRepo.save(toDo);//simple save method , saves this toDo into the record
    }

    //RETRIEVE
    public ToDo getToDo(long id) throws ToDoNotFoundException{
        return toDoRepo.findById(id).orElseThrow(() -> new ToDoNotFoundException("ToDo with ID : " + id + "doesnt Exist!"));
    }

    //RETRIEVE ALL todo list
    public List<ToDo> getToDoList(Boolean status, String dueDate, String taskDesc){

        //we assign the returned specification instance to the spec variable
        Specification<ToDo> spec = ToDoSpecification.findByCriteria(status, dueDate, taskDesc);
        //findByCriteria method returns the Specification instance
        //“Give me a Specification object that knows how to filter ToDos based on these three possible parameters.”

        //now using this specification instance spec we tell JPA to start constructing the query
       // List<ToDo> toDoList = toDoRepo.findAll(spec); //this findAll(Specification<ToDo>) method is from the JPASpecificationExecutor
        //this returns the list of ToDo objects because we will be using the specification<ToDo>
        //we are basically telling the JPA -> “Give me all ToDo records that satisfy the conditions described by this Specification.”
        return toDoRepo.findAll(spec);//this list is a list of todo objects after fitering
        // return toDoRepo.findAllByOrderById(status, dueDate, taskDesc);
    }

    //public List<ToDo> getToDoListByStatus(boolean status){return toDoRepo.findAllByStatusOrderById(status);}

    //SELECTIVE todo UPDATE
    public ToDo updateToDo(long id, ToDo updatedToDo) throws ToDoNotFoundException{

        //better to retrive it directly than simply checking if it exists by id or not
        ToDo existingToDo = toDoRepo.findById(id).orElseThrow(() -> new ToDoNotFoundException("ToDo with ID : " + id + "doesnt Exist!"));
        //orElseThrow takes in an exception supplier, the one that supplies exception,

        //THERE FORE WE DONT NEED TO CHECK IF THE ELEMENT IS PRESENT OR NOT FIRST, UPPER LINE HANDLES IT
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

        //STEP 3:  SAVE the updated
        return toDoRepo.save(existingToDo);
        //Return Type <S extends T>:-	T or any subtype of T which is 'S' The method returns the object that was saved.
        // It returns type T or any of its subtype (T extends S) to ensure it returns the most specific type possible
        // (e.g., if you save a SpecialToDo, it returns a SpecialToDo). -- S is a special type or subtype ,
        //T is ToDo here and S is some special subtype of ToDo, since T is the main class that the repository is managing
        //S is just "a-ny class that extends the ToDo class"    <T,  ID>
        //public interface ToDoRepository extends JpaRepository<ToDo, Long> {
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