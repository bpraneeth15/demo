package com.example.demo.Specification;

import com.example.demo.model.ToDo;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class ToDoSpecification {

    public static Specification<ToDo> findByCriteria(Boolean status, String dueDate, String taskDesc){

        return new Specification<ToDo>() {
            //Implements the toPredicate method (the logic for how to build the actual database condition)
            @Override
            public Predicate toPredicate(Root<ToDo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //some logic to build a predicate
                //we build this using 3 objects, root, criteriaQuery and criteriaBuilder
                //root-> table/entity that we are querying and its columns , example root.get("status"), root.get("taskDesc")
                //get() method -> <Y> Path<Y> get(String attributeName); this returns a path object that points to the field(mentioned as the attributeName) in the database that is
                //It’s generic: you decide what Y is, based on the field’s type.
                // Path<Y> object that represents that field (or “path”) in the query.
                //root.get("status") → returns a Path<Boolean>, root.get("taskDesc") → returns a Path<String>
                //
                Predicate predicateStatus = criteriaBuilder.equal(root.get("status"), status); //the equal method returns a predicate
                Predicate predicateTaskDesc = criteriaBuilder.like(root.get("taskDesc"), taskDesc);//even if we use like, this predicate behaves exactly as equal because there are no wildcards being used
               //lower method signature:- Expression<String> lower(Expression<String> var1);
                //“Take the column represented by this Path object, and wrap it in the SQL function LOWER(...).”
                //result is a new Expression object representing LOWER(task_desc).
                //Predicate predicateDueDate = criteriaBuilder.like(root.get("taskDesc"), taskDesc);
                return criteriaBuilder.or(predicateStatus, predicateTaskDesc);}
        };

    }
}
