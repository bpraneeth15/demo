package com.example.demo.Specification;

import com.example.demo.model.ToDo;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ToDoSpecification {

    public static Specification<ToDo> findByCriteria(Boolean status, String dueDate, String taskDesc){

        return new Specification<ToDo>() {
            //Implements the toPredicate method (the logic for how to build the actual database condition)
            @Override
            public Predicate toPredicate(Root<ToDo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                //create a list for storing each predicate that we want to use given the field names in the query
                List<Predicate> predicateList = new ArrayList<>();

                //step 2 : add each prediccacte to the list based on the giveen field names in the query
                if(status != null){
                    //if the status is not null then add the predicate to the list
                    predicateList.add(criteriaBuilder.equal(root.get("status"), status));
                }

                if(taskDesc != null && !taskDesc.trim().isEmpty() ){
                    //“Add a new condition that checks whether the lowercase version of taskDesc in the database contains the lowercase text given by the user, anywhere inside it.”
                    predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("taskDesc")),"%"+taskDesc.toLowerCase(Locale.ROOT)+"%"));
                }

                if(dueDate != null && !dueDate.trim().isEmpty()){
                    predicateList.add(criteriaBuilder.equal(root.get("dueDate"), dueDate));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));}
        };

    }
}
