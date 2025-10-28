package com.example.demo.repository;

import com.example.demo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/*You need to tell it two things inside the angle brackets (< >):
1. The Entity class it manages (the object it saves and loads).
2. The Wrapper Type of that Entity's Primary Key.*/
@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long>, JpaSpecificationExecutor<ToDo> {

}
