package com.todo.spring.restfulservices.todosservices.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.todo.spring.restfulservices.todosservices.entity.Todo;

@Service
public class TodoService {

	private static List<Todo> todos = new ArrayList<>();
	private static Long idCounter = 0L;

	static {
		todos.add(new Todo(++idCounter, "anand", "Learn to Dance 2", false, new Date()));
		todos.add(new Todo(++idCounter, "anand","Learn AngularJs 2", false, new Date()));
		todos.add(new Todo(++idCounter, "anand", "Travel around world 2", false, new Date())) ;
		todos.add(new Todo(++idCounter, "raj", "Travel around world 2", false, new Date())); 
		todos.add(new Todo(++idCounter, "raj", "Skuba Diving 2", false, new Date()));
	}

	public List<Todo> findAll(){
		return todos;
	}

	public Todo createTodo(Todo todoBean) {
		if(todoBean.getId() == -1 || todoBean.getId() == 0) {
			todoBean.setId(++idCounter);
		} else {
			deleteById(todoBean.getId());
		}

		todos.add(todoBean);
		return todoBean;
	}

	public Todo deleteById(Long id) {
		Todo todoBean = findById(id);

		if(todoBean != null) {
			todos.remove(todoBean);
		}

		return todoBean;
	}

	public Todo findById(Long id) {
		for(Todo todo : todos) {
			if(todo.getId() == id) {
				return todo;
			}
		}
		return null;
	}
}
