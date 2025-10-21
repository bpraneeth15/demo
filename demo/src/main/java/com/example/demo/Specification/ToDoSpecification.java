package com.example.demo.Specification;

import com.example.demo.model.ToDo;
import org.springframework.data.jpa.domain.Specification;

public class ToDoSpecification {

    //this is the method(a tool) for building the filter
    //Static helper method to build a dynamic Specification filter for ToDo entities
    public static Specification<ToDo> findByCriteria(){
        //we call this and we get a finished product which is the specification filter
        //1. create an empty list to hold our filter conditions
        return  null;
    }
}
//So, the process inside our method is:
//
//Create an empty list to hold our filter conditions (our Predicates).
//
//Check if status was provided. If yes, use the criteriaBuilder to create an "equals" condition and add it to the list.
//
//Check if dueDate was provided. If yes, use the criteriaBuilder to create another "equals" condition and add it to the list.
//
//Finally, tell the criteriaBuilder to combine all conditions in our list with "AND."