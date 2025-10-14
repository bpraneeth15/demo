package com.example.demo.repository;

import com.example.demo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*@Repository annotation is a specialization of @Component that serves two crucial purposes in your persistence layer:
1. Component Scanning: It explicitly marks your ToDoRepository interface as a Data Access Object (DAO).
This tells Spring's automatic component scanning process: "Find this interface,
and automatically create a complete, working class implementation for it."
//2. Exception Translation: It enables automatic exception translation. This is a powerful feature where Spring intercepts messy,
low-level database errors (like a PostgreSQL "connection refused" error) and converts them into clean, standardized Spring DataAccessException types.
This makes error handling much easier for you.*/

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
/*You need to tell it two things inside the angle brackets (< >):
1. The Entity class it manages (the object it saves and loads).
2. The Wrapper Type of that Entity's Primary Key.*/



}
