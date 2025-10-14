package com.example.demo.exception;

public class ToDoNotFoundException extends Exception{

    // Constructs a new ToDoNotFoundException with the detailed message
    public ToDoNotFoundException(String message){
        super(message);
    }
}
