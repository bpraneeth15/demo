package com.example.demo.Specification;

import com.example.demo.model.ToDo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ToDoSpecification {

    public static Specification<ToDo> findByCriteria(Boolean status, String dueDate, String taskDesc){

        //here we have to return a specification object/instance, therefore we implement the toPredicate method here
        //with a lambda expression
        //Creates a new Specification instance using an anonymous class (good choice — that’s explicit and clear).

        return new Specification<ToDo>() {
            //Implements the toPredicate method (the logic for how to build the actual database condition)
            @Override
            public Predicate toPredicate(Root<ToDo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //some logic to build a predicate
                //we build this using 3 objects, root, criteriaQuery and criteriaBuilder
                //root-> table/entity that we are querying and its columns , example root.get("status"), root.get("taskDesc")
                //get() method -> <Y> Path<Y> get(String attributeName); this returns a path object that points to the field(mentioned as the attributeName) in the database that is
                //root.get("status") → returns a Path<Boolean>, root.get("taskDesc") → returns a Path<String>
//this is a static filter, hardcoded values
                Predicate predicateStatus = criteriaBuilder.equal(root.get("status"), status); //the equal method returns a predicate
                Predicate predicateTaskDesc = criteriaBuilder.like(root.get("taskDesc"), taskDesc);
                return criteriaBuilder.and(predicateStatus, predicateTaskDesc);}
        };

    }
}
/*Existing logic flaws:
* Problem:           	Why it happens:
Null values	            If status or taskDesc is null, cb.equal() or cb.like() will throw an error or generate incorrect SQL.
Static filtering	    You’re building both predicates every time, even if the user didn’t pass those parameters.
Rigid query	            It always applies both filters — you can’t handle flexible combinations.
*
* In the next version instead of creating both the predicates unnecessarily we will use an arrayList to keep track of what predicates to build,
* think of it like a predicate bag, that collects the conditions dynamically. we will add each predicate to build to this array list
* only if its corresponding parameter is present in the request.(i.e, not null).
*
* */